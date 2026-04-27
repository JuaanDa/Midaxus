package com.example.Midaxus.services;

import com.example.Midaxus.model.dtos.SubjectDTO;
import com.example.Midaxus.model.entities.Subject;
import com.example.Midaxus.model.mapper.SubjectMapper;
import com.example.Midaxus.repositories.SubjectRepository;
import com.example.Midaxus.model.entities.InstitutionPolicy;
import com.example.Midaxus.repository.InstitutionPolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService implements ISubject<SubjectDTO, String> {

    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private InstitutionPolicyRepository policyRepository;

    // 🔹 CREATE
    @Override
    public SubjectDTO create(SubjectDTO subjectDTO) {

        InstitutionPolicy policy = policyRepository.findById(1L).orElseGet(() -> {
            InstitutionPolicy p = new InstitutionPolicy();
            p.setMaxSessionsPerWeek(4);
            return p;
        });

        // 🔥 VALIDACIÓN HU-11: Rango de sesiones parametrizado
        int maxSessions = policy.getMaxSessionsPerWeek() != null ? policy.getMaxSessionsPerWeek() : 4;
        
        if (subjectDTO.getSessionPerWeek() < 1 || subjectDTO.getSessionPerWeek() > maxSessions) {
            throw new RuntimeException("Sesiones por semana inválidas (1-" + maxSessions + ")");
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