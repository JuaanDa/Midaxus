package com.example.Midaxus.services;

import com.example.Midaxus.model.dtos.InstitutionPolicyDTO;
import com.example.Midaxus.model.entities.InstitutionPolicy;
import com.example.Midaxus.repository.InstitutionPolicyRepository;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class InstitutionPolicyService implements IInstitutionPolicy {

    private final InstitutionPolicyRepository repository;

    public InstitutionPolicyService(InstitutionPolicyRepository repository) {
        this.repository = repository;
    }

    @Override
    public InstitutionPolicyDTO getPolicy() {
        InstitutionPolicy policy = repository.findById(1L).orElseGet(() -> {
            InstitutionPolicy defaultPolicy = new InstitutionPolicy();
            defaultPolicy.setClassStartTime(LocalTime.of(8, 0));
            defaultPolicy.setClassEndTime(LocalTime.of(18, 0));
            defaultPolicy.setLunchStartTime(LocalTime.of(12, 0));
            defaultPolicy.setLunchEndTime(LocalTime.of(13, 30));
            defaultPolicy.setStandardCapacity(40);
            defaultPolicy.setCapacityTolerancePercent(10);
            return repository.save(defaultPolicy);
        });
        return toDTO(policy);
    }

    @Override
    public InstitutionPolicyDTO updatePolicy(InstitutionPolicyDTO dto) {
        InstitutionPolicy policy = repository.findById(1L).orElse(new InstitutionPolicy());
        
        if (dto.getClassStartTime() != null) policy.setClassStartTime(dto.getClassStartTime());
        if (dto.getClassEndTime() != null) policy.setClassEndTime(dto.getClassEndTime());
        if (dto.getLunchStartTime() != null) policy.setLunchStartTime(dto.getLunchStartTime());
        if (dto.getLunchEndTime() != null) policy.setLunchEndTime(dto.getLunchEndTime());
        if (dto.getStandardCapacity() != null) policy.setStandardCapacity(dto.getStandardCapacity());
        if (dto.getCapacityTolerancePercent() != null) policy.setCapacityTolerancePercent(dto.getCapacityTolerancePercent());
        
        policy = repository.save(policy);
        return toDTO(policy);
    }

    private InstitutionPolicyDTO toDTO(InstitutionPolicy entity) {
        InstitutionPolicyDTO dto = new InstitutionPolicyDTO();
        dto.setId(entity.getId());
        dto.setClassStartTime(entity.getClassStartTime());
        dto.setClassEndTime(entity.getClassEndTime());
        dto.setLunchStartTime(entity.getLunchStartTime());
        dto.setLunchEndTime(entity.getLunchEndTime());
        dto.setStandardCapacity(entity.getStandardCapacity());
        dto.setCapacityTolerancePercent(entity.getCapacityTolerancePercent());
        return dto;
    }
}
