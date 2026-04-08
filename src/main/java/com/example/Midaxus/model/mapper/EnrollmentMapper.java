package com.example.Midaxus.model.mapper;

import com.example.Midaxus.model.dtos.EnrollmentDTO;
import com.example.Midaxus.model.entities.Enrollment;

import java.util.List;

public class EnrollmentMapper {

    public static EnrollmentDTO toDTO(Enrollment enrollment){
        return new EnrollmentDTO(enrollment.getEnrollmentId(), enrollment.getStudent(),
                                                enrollment.getCourseGroup(), enrollment.getStatus());
    }

    public static Enrollment toEntity(EnrollmentDTO dto){
        Enrollment enrollment = new Enrollment();
        enrollment.setEnrollmentId(dto.getEnrollmentId());
        enrollment.setStudent(dto.getStudent());
        enrollment.setCourseGroup(dto.getCourseGroup());
        enrollment.setStatus(dto.getStatus());
        return enrollment;

    }

    public static List<EnrollmentDTO> all(List<Enrollment>dtos){
        if (dtos == null) return null;
        return dtos.stream().map(EnrollmentMapper::toDTO).toList();
    }
}
