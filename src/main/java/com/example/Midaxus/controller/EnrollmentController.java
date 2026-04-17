package com.example.Midaxus.controller;

import com.example.Midaxus.model.dtos.EnrollmentDTO;
import com.example.Midaxus.services.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    //  CREATE (inscribir estudiante)
    @PostMapping
    public ResponseEntity<EnrollmentDTO> create(@RequestBody EnrollmentDTO dto) {

        EnrollmentDTO created = enrollmentService.createEnrollment(dto);

        return ResponseEntity
                .created(URI.create("/api/enrollments/" + created.getEnrollmentId()))
                .body(created);
    }

    //  GET ALL
    @GetMapping
    public ResponseEntity<List<EnrollmentDTO>> getAll() {
        return ResponseEntity.ok(enrollmentService.getAll());
    }

    //  GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<EnrollmentDTO> getById(@PathVariable String id) {
        return ResponseEntity.ok(enrollmentService.getEnrollment(id));
    }

    //  DELETE (soft delete → DROPPED)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        enrollmentService.deleteEnrollment(id);
        return ResponseEntity.noContent().build();
    }
}