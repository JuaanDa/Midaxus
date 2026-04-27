package com.example.Midaxus.services;

import com.example.Midaxus.model.dtos.InstitutionPolicyDTO;

public interface IInstitutionPolicy {
    InstitutionPolicyDTO getPolicy();
    InstitutionPolicyDTO updatePolicy(InstitutionPolicyDTO dto);
}
