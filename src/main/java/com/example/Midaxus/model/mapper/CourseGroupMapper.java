package com.example.Midaxus.model.mapper;

import com.example.Midaxus.model.dtos.CourseGroupDTO;
import com.example.Midaxus.model.entities.CourseGroup;

import java.util.List;

public class CourseGroupMapper {


    public static CourseGroupDTO toDTO(CourseGroup entity) {

        if (entity == null) return null;

        return new CourseGroupDTO(
                entity.getCourseGroupId(),
                entity.getTeacher() != null ? entity.getTeacher().getId() : null,
                entity.getSubject() != null ? entity.getSubject().getIdSubject() : null,
                entity.getAcademicPeriod() != null ? entity.getAcademicPeriod().getPeriodId() : null,
                entity.getCode(),
                entity.getCapacity()
        );
    }


    public static CourseGroup toEntity(CourseGroupDTO dto) {

        if (dto == null) return null;

        CourseGroup entity = new CourseGroup();

        entity.setCourseGroupId(dto.getCourseGroupId());
        entity.setCode(dto.getCode());
        entity.setCapacity(dto.getCapacity());

        return entity;
    }


    public static List<CourseGroupDTO> toDTOList(List<CourseGroup> list) {

        if (list == null) return List.of();

        return list.stream()
                .map(CourseGroupMapper::toDTO)
                .toList();
    }
}