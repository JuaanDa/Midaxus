package com.example.Midaxus.controller;

import com.example.Midaxus.model.dtos.InstitutionPolicyDTO;
import com.example.Midaxus.services.IInstitutionPolicy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/policies")
public class InstitutionPolicyController {

    private final IInstitutionPolicy service;

    public InstitutionPolicyController(IInstitutionPolicy service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<InstitutionPolicyDTO> getPolicy() {
        return ResponseEntity.ok(service.getPolicy());
    }

    @PutMapping
    public ResponseEntity<InstitutionPolicyDTO> updatePolicy(@RequestBody InstitutionPolicyDTO dto) {
        return ResponseEntity.ok(service.updatePolicy(dto));
    }
}
