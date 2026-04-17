package com.example.Midaxus.model.mapper;

import com.example.Midaxus.model.dtos.EnrollmentDTO;
import com.example.Midaxus.model.entities.CourseGroup;
import com.example.Midaxus.model.entities.Enrollment;
import com.example.Midaxus.model.entities.Student;

import java.util.List;

public class EnrollmentMapper {


    public static EnrollmentDTO toDTO(Enrollment enrollment){
        if (enrollment == null) return null;

        return new EnrollmentDTO(
                enrollment.getEnrollmentId(),
                enrollment.getStudent().getStudentId(),
                enrollment.getCourseGroup().getCourseGroupId(),
                enrollment.getStatus()
        );
    }


    public static Enrollment toEntity(EnrollmentDTO dto){
        if (dto == null) return null;

        Enrollment enrollment = new Enrollment();
        enrollment.setEnrollmentId(dto.getEnrollmentId());
        enrollment.setStatus(dto.getStatus());

        return enrollment;
    }


    public static List<EnrollmentDTO> toDTOList(List<Enrollment> enrollments){
        if (enrollments == null) return List.of();

        return enrollments.stream()
                .map(EnrollmentMapper::toDTO)
                .toList();
    }
}