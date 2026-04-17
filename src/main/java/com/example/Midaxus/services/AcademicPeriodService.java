package com.example.Midaxus.services;

import com.example.Midaxus.model.dtos.AcademicPeriodDTO;
import com.example.Midaxus.model.entities.AcademicPeriod;
import com.example.Midaxus.model.mapper.AcademicPeriodMapper;
import com.example.Midaxus.repositories.AcademicPeriodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AcademicPeriodService implements IAcademicPeriod<AcademicPeriodDTO, String> {

    @Autowired
    private AcademicPeriodRepository academicPeriodRepository;


    @Override
    public AcademicPeriodDTO create(AcademicPeriodDTO dto) {

        if (dto == null) throw new RuntimeException("Datos inválidos");


        if (dto.getStartDate().isAfter(dto.getEndDate())) {
            throw new RuntimeException("La fecha de inicio no puede ser mayor que la de fin");
        }

        if (dto.getEnrollmentStartDate().isAfter(dto.getEnrollmentEndDate())) {
            throw new RuntimeException("Fechas de inscripción inválidas");
        }

        AcademicPeriod entity = AcademicPeriodMapper.toEntity(dto);

        AcademicPeriod saved = academicPeriodRepository.save(entity);

        return AcademicPeriodMapper.toDTO(saved);
    }


    @Override
    public AcademicPeriodDTO getById(String id) {

        AcademicPeriod entity = academicPeriodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Periodo no encontrado"));

        return AcademicPeriodMapper.toDTO(entity);
    }


    @Override
    public List<AcademicPeriodDTO> getAll() {
        return AcademicPeriodMapper.toDTOList(academicPeriodRepository.findAll());
    }


    @Override
    public void delete(String id) {

        if (!academicPeriodRepository.existsById(id)) {
            throw new RuntimeException("Periodo no existe");
        }

        academicPeriodRepository.deleteById(id);
    }
}

