package com.example.Midaxus.controller;

import com.example.Midaxus.model.dtos.AcademicPeriodDTO;
import com.example.Midaxus.services.AcademicPeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/academic-periods")
public class AcademicPeriodController {

    @Autowired
    private AcademicPeriodService service;

    // 🔹 CREATE
    @PostMapping
    public ResponseEntity<AcademicPeriodDTO> create(@RequestBody AcademicPeriodDTO dto) {

        AcademicPeriodDTO created = service.create(dto);

        return ResponseEntity
                .created(URI.create("/api/academic-periods/" + created.getPeriodId()))
                .body(created);
    }

    // 🔹 GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<AcademicPeriodDTO> getById(@PathVariable String id) {

        AcademicPeriodDTO result = service.getById(id);

        return ResponseEntity.ok(result);
    }

    // 🔹 GET ALL
    @GetMapping
    public ResponseEntity<List<AcademicPeriodDTO>> getAll() {

        return ResponseEntity.ok(service.getAll());
    }

    // 🔹 DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {

        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}