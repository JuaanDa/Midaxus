package com.example.Midaxus.controller;

import com.example.Midaxus.model.dtos.CourseGroupDTO;
import com.example.Midaxus.services.CourseGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/course-groups")
public class CourseGroupController {

    @Autowired
    private CourseGroupService service;

    //  CREATE
    @PostMapping
    public ResponseEntity<CourseGroupDTO> create(@RequestBody CourseGroupDTO dto) {

        CourseGroupDTO created = service.create(dto);

        return ResponseEntity
                .created(URI.create("/api/course-groups/" + created.getCourseGroupId()))
                .body(created);
    }

    //  UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<CourseGroupDTO> update(@PathVariable String id, @RequestBody CourseGroupDTO dto) {
        CourseGroupDTO updated = service.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    //  GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<CourseGroupDTO> getById(@PathVariable String id) {

        return ResponseEntity.ok(service.getById(id));
    }

    //  GET ALL
    @GetMapping
    public ResponseEntity<List<CourseGroupDTO>> getAll() {

        return ResponseEntity.ok(service.getAll());
    }

    //  DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {

        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    //  GET BY TEACHER
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<CourseGroupDTO>> getByTeacher(@PathVariable String teacherId) {

        return ResponseEntity.ok(service.getByTeacher(teacherId));
    }

    //  GET BY SUBJECT
    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<List<CourseGroupDTO>> getBySubject(@PathVariable String subjectId) {

        return ResponseEntity.ok(service.getBySubject(subjectId));
    }
}