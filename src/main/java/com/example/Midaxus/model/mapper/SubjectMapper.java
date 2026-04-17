package com.example.Midaxus.model.mapper;

import com.example.Midaxus.model.dtos.SubjectDTO;
import com.example.Midaxus.model.entities.Subject;

import java.util.Collections;
import java.util.List;

public class SubjectMapper {
    public static SubjectDTO toDTO(Subject subject) {

        if (subject == null) return null;

        return new SubjectDTO(
                subject.getIdSubject(),
                subject.getSubjectName(),
                subject.getSessionPerWeek(),
                subject.getDurationMinutes()
        );
    }

    // 🔹 DTO → ENTITY
    public static Subject toEntity(SubjectDTO dto) {

        if (dto == null) return null;

        Subject subject = new Subject();

        subject.setIdSubject(dto.getIdSubject());
        subject.setSubjectName(dto.getSubjectName());
        subject.setSessionPerWeek(dto.getSessionPerWeek());
        subject.setDurationMinutes(dto.getDurationMinutes());

        return subject;
    }

    // 🔹 LIST → DTO LIST
    public static List<SubjectDTO> toDTOList(List<Subject> subjects) {

        if (subjects == null) return Collections.emptyList();

        return subjects.stream()
                .map(SubjectMapper::toDTO)
                .toList();
    }

}
