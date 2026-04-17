package com.example.Midaxus.controller;

import com.example.Midaxus.model.dtos.SubjectDTO;
import com.example.Midaxus.services.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;


    @PostMapping
    public ResponseEntity<SubjectDTO> create(@RequestBody SubjectDTO dto) {

        SubjectDTO created = subjectService.create(dto);

        return ResponseEntity
                .created(URI.create("/api/subjects/" + created.getIdSubject()))
                .body(created);
    }


    @GetMapping
    public ResponseEntity<List<SubjectDTO>> findAll() {
        return ResponseEntity.ok(subjectService.findAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<SubjectDTO> findById(@PathVariable String id) {
        return ResponseEntity.ok(subjectService.findById(id));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        subjectService.delete(id);
        return ResponseEntity.noContent().build();
    }
}