package com.example.Midaxus.services;

import com.example.Midaxus.model.dtos.SubjectDTO;
import com.example.Midaxus.model.entities.Subject;
import com.example.Midaxus.model.mapper.SubjectMapper;
import com.example.Midaxus.repositories.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService implements ISubject<SubjectDTO, String> {

    @Autowired
    private SubjectRepository subjectRepository;

    // 🔹 CREATE
    @Override
    public SubjectDTO create(SubjectDTO subjectDTO) {

        // 🔥 VALIDACIÓN RF-04
        if (subjectDTO.getSessionPerWeek() < 1 || subjectDTO.getSessionPerWeek() > 4) {
            throw new RuntimeException("Sesiones por semana inválidas (1-4)");
        }

        if (subjectDTO.getDurationMinutes() != 120) {
            throw new RuntimeException("Cada sesión debe durar 120 minutos");
        }

        Subject subject = SubjectMapper.toEntity(subjectDTO);

        Subject saved = subjectRepository.save(subject);

        return SubjectMapper.toDTO(saved);
    }


    @Override
    public void delete(String id) {
        if (!subjectRepository.existsById(id)) {
            throw new RuntimeException("Subject no encontrado");
        }

        subjectRepository.deleteById(id);
    }


    @Override
    public List<SubjectDTO> findAll() {
        return SubjectMapper.toDTOList(subjectRepository.findAll());
    }


    @Override
    public SubjectDTO findById(String id) {

        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject no encontrado"));

        return SubjectMapper.toDTO(subject);
    }
}