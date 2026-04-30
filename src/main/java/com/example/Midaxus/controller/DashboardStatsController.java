package com.example.Midaxus.controller;

import com.example.Midaxus.model.enums.EnrollmentStatus;
import com.example.Midaxus.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Endpoint que devuelve estadísticas reales del dashboard
 * para cada rol (ADMIN, TEACHER, STUDENT).
 */
@RestController
@RequestMapping("/api/dashboard")
public class DashboardStatsController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private CourseGroupRepository courseGroupRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private ScheduleSessionRepository scheduleSessionRepository;

    /**
     * GET /api/dashboard/stats/admin
     * Retorna: totalStudents, totalTeachers, totalSubjects, totalRooms
     */
    @GetMapping("/stats/admin")
    public ResponseEntity<Map<String, Object>> getAdminStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalStudents", studentRepository.count());
        stats.put("totalTeachers", teacherRepository.count());
        stats.put("totalSubjects", subjectRepository.count());
        stats.put("totalRooms", roomRepository.count());
        stats.put("totalCourseGroups", courseGroupRepository.count());
        stats.put("totalEnrollments", enrollmentRepository.findAllByStatus(EnrollmentStatus.ENROLLED).size());
        return ResponseEntity.ok(stats);
    }

    /**
     * GET /api/dashboard/stats/teacher/{teacherCode}
     * Retorna: myCourses, weeklyHours, myGroups
     */
    @GetMapping("/stats/teacher/{teacherCode}")
    public ResponseEntity<Map<String, Object>> getTeacherStats(@PathVariable String teacherCode) {
        Map<String, Object> stats = new HashMap<>();

        long myCourses = courseGroupRepository.findByTeacher_TeacherCode(teacherCode).size();
        stats.put("myCourses", myCourses);

        // Cada sesión de clase dura 2 horas (120 min) según regla de negocio
        // weeklyHours = cantidad de schedule sessions del profesor × 2
        long scheduleSessions = scheduleSessionRepository.findAll().stream()
                .filter(ss -> ss.getCourseGroup() != null
                        && ss.getCourseGroup().getTeacher() != null
                        && teacherCode.equals(ss.getCourseGroup().getTeacher().getTeacherCode()))
                .count();

        // Si no hay schedule sessions registradas, calcular por las materias
        // (cada curso tiene sessionPerWeek definido en el Subject × 2h)
        if (scheduleSessions == 0 && myCourses > 0) {
            long estimatedHours = courseGroupRepository.findByTeacher_TeacherCode(teacherCode).stream()
                    .mapToLong(cg -> {
                        if (cg.getSubject() != null && cg.getSubject().getSessionPerWeek() > 0) {
                            return cg.getSubject().getSessionPerWeek() * 2L;
                        }
                        return 2L; // fallback: al menos 2h por curso
                    })
                    .sum();
            stats.put("weeklyHours", estimatedHours);
        } else {
            stats.put("weeklyHours", scheduleSessions * 2);
        }

        stats.put("myGroups", myCourses);
        return ResponseEntity.ok(stats);
    }

    /**
     * GET /api/dashboard/stats/student/{studentId}
     * Retorna: enrolledCourses
     */
    @GetMapping("/stats/student/{studentId}")
    public ResponseEntity<Map<String, Object>> getStudentStats(@PathVariable String studentId) {
        Map<String, Object> stats = new HashMap<>();

        long enrolledCount = enrollmentRepository
                .findByStudent_StudentIdAndStatus(studentId, EnrollmentStatus.ENROLLED)
                .size();

        stats.put("enrolledCourses", enrolledCount);
        return ResponseEntity.ok(stats);
    }
}
