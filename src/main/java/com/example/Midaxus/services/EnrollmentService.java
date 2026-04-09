package com.example.Midaxus.services;

import com.example.Midaxus.model.dtos.EnrollmentDTO;
import com.example.Midaxus.model.entities.CourseGroup;
import com.example.Midaxus.model.entities.Enrollment;
import com.example.Midaxus.model.entities.Student;
import com.example.Midaxus.model.enums.EnrollmentStatus;
import com.example.Midaxus.model.mapper.EnrollmentMapper;
import com.example.Midaxus.repositories.CourseGroupRepository;
import com.example.Midaxus.repositories.EnrollmentRepository;
import com.example.Midaxus.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EnrollmentService implements IEnrollment<EnrollmentDTO, String> {

    @Autowired
    EnrollmentRepository enrollmentRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    CourseGroupRepository courseGroupRepository;


    @Override
    public EnrollmentDTO createEnrollment(EnrollmentDTO dto) {

        if (dto == null) throw new RuntimeException("Datos inválidos");

        Student student = studentRepository.findById(dto.getStudent().getId())
                .orElseThrow(() -> new RuntimeException("Student no encontrado"));

        CourseGroup courseGroup = courseGroupRepository.findById(dto.getCourseGroup().getCourseGroupId())
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        // 🔥 duplicado
        if (enrollmentRepository.existsByStudentAndCourseGroup(student, courseGroup)) {
            throw new RuntimeException("Ya estás inscrito en este curso");
        }

        // 🔥 capacidad
        if (courseGroup.getEnrollments().size() >= courseGroup.getCapacity()) {
            throw new RuntimeException("Curso lleno");
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourseGroup(courseGroup);
        enrollment.setStatus(EnrollmentStatus.ENROLLED);

        Enrollment saved = enrollmentRepository.save(enrollment);

        return EnrollmentMapper.toDTO(saved);
    }

    @Override
    public EnrollmentDTO getEnrollment(String s) {
        Enrollment e = enrollmentRepository.findById(s)
                .orElseThrow(() -> new RuntimeException("No encontrado"));
        return EnrollmentMapper.toDTO(e);

    }

    @Override
    public List<EnrollmentDTO> getAll() {
        return EnrollmentMapper.all(enrollmentRepository.findAllByStatus(EnrollmentStatus.ENROLLED));
    }

    @Override
    public void deleteEnrollment(String id) {

        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment no encontrado"));

        if (enrollment.getStatus() == EnrollmentStatus.DROPPED) {
            throw new RuntimeException("El estudiante ya se retiró de este curso");
        }

        enrollment.setStatus(EnrollmentStatus.DROPPED);

        enrollmentRepository.save(enrollment);
    }
}
