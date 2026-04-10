package com.example.Midaxus.controller;

import com.example.Midaxus.model.dtos.TeacherDTO;
import com.example.Midaxus.services.ITeacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    @Autowired
    private ITeacher<TeacherDTO, String> teacherService;

    @PostMapping
    public ResponseEntity<TeacherDTO> createTeacher(@RequestBody TeacherDTO dto) {
        TeacherDTO done = teacherService.createTeacher(dto);
        return ResponseEntity
                .created(URI.create("/api/teachers/" + done.getTeacherCode()))
                .body(done);
    }

    @GetMapping
    public ResponseEntity<List<TeacherDTO>> getTeachers() {
        return ResponseEntity.ok(teacherService.getTeachers());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable String id) {
        teacherService.deleteTeacher(id);
        return ResponseEntity.noContent().build();
    }
}