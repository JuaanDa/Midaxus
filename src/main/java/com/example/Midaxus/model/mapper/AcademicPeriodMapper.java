package com.example.Midaxus.model.mapper;

import com.example.Midaxus.model.dtos.AcademicPeriodDTO;
import com.example.Midaxus.model.entities.AcademicPeriod;

import java.util.List;

public class AcademicPeriodMapper {


    public static AcademicPeriodDTO toDTO(AcademicPeriod entity) {

        if (entity == null) return null;

        return new AcademicPeriodDTO(
                entity.getPeriodId(),
                entity.getCode(),
                entity.getDescription(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getEnrollmentStartDate(),
                entity.getEnrollmentEndDate()
        );
    }


    public static AcademicPeriod toEntity(AcademicPeriodDTO dto) {

        if (dto == null) return null;

        AcademicPeriod entity = new AcademicPeriod();

        entity.setPeriodId(dto.getPeriodId());
        entity.setCode(dto.getCode());
        entity.setDescription(dto.getDescription());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        entity.setEnrollmentStartDate(dto.getEnrollmentStartDate());
        entity.setEnrollmentEndDate(dto.getEnrollmentEndDate());

        return entity;
    }


    public static List<AcademicPeriodDTO> toDTOList(List<AcademicPeriod> list) {

        if (list == null) return List.of();

        return list.stream()
                .map(AcademicPeriodMapper::toDTO)
                .toList();
    }
}