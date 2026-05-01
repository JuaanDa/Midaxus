package com.example.Midaxus.services;

import com.example.Midaxus.model.dtos.StudentScheduleSlotDTO;
import com.example.Midaxus.model.entities.CourseGroup;
import com.example.Midaxus.model.entities.Student;
import com.example.Midaxus.model.entities.StudentScheduleSlot;
import com.example.Midaxus.model.entities.Subject;
import com.example.Midaxus.repositories.CourseGroupRepository;
import com.example.Midaxus.repositories.StudentRepository;
import com.example.Midaxus.repositories.StudentScheduleSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentScheduleService {

    @Autowired
    private StudentScheduleSlotRepository scheduleRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseGroupRepository courseGroupRepository;

    @Transactional
    public void saveStudentSchedule(String studentId, List<StudentScheduleSlotDTO> slots) {
        Student student = findStudentFlexible(studentId);

        // Delete previous schedule
        scheduleRepository.deleteByStudent(student);

        if (slots == null || slots.isEmpty()) return;

        List<StudentScheduleSlot> entities = new ArrayList<>();
        for (StudentScheduleSlotDTO dto : slots) {
            CourseGroup cg = courseGroupRepository.findById(dto.getCourseGroupId())
                    .orElseThrow(() -> new RuntimeException("Grupo de curso no encontrado"));
            
            DayOfWeek day;
            try {
                day = DayOfWeek.valueOf(dto.getDay().toUpperCase());
            } catch (Exception e) {
                // If it fails to parse, try to handle Spanish days or just continue
                continue; 
            }

            StudentScheduleSlot entity = new StudentScheduleSlot(student, cg, day, dto.getSlot());
            entities.add(entity);
        }

        scheduleRepository.saveAll(entities);
    }

    @Transactional(readOnly = true)
    public List<StudentScheduleSlotDTO> getStudentSchedule(String studentId) {
        Student student = findStudentFlexible(studentId);
        List<StudentScheduleSlot> entities = scheduleRepository.findByStudent(student);
        List<StudentScheduleSlotDTO> dtos = new ArrayList<>();

        for (StudentScheduleSlot entity : entities) {
            StudentScheduleSlotDTO dto = new StudentScheduleSlotDTO();
            dto.setCourseGroupId(entity.getCourseGroup().getCourseGroupId());
            dto.setCourseCode(entity.getCourseGroup().getCode());
            
            Subject subject = entity.getCourseGroup().getSubject();
            dto.setSubjectName(subject != null ? subject.getSubjectName() : entity.getCourseGroup().getCode());
            
            dto.setDay(entity.getDay().name());
            dto.setSlot(entity.getTimeSlot());
            dtos.add(dto);
        }

        return dtos;
    }

    private Student findStudentFlexible(String identifier) {
        return studentRepository.findById(identifier)
                .orElseGet(() -> studentRepository.findByStudentId(identifier)
                        .orElseGet(() -> studentRepository.findByEmail(identifier)
                                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado: " + identifier))));
    }
}
