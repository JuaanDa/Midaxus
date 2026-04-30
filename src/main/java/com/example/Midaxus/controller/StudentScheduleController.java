package com.example.Midaxus.controller;

import com.example.Midaxus.model.dtos.ApiResponse;
import com.example.Midaxus.model.dtos.StudentScheduleSlotDTO;
import com.example.Midaxus.services.StudentScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student-schedules")
public class StudentScheduleController {

    @Autowired
    private StudentScheduleService studentScheduleService;

    @PostMapping("/{studentId}")
    public ResponseEntity<ApiResponse<Void>> saveSchedule(
            @PathVariable String studentId,
            @RequestBody List<StudentScheduleSlotDTO> slots) {
        
        studentScheduleService.saveStudentSchedule(studentId, slots);
        return ResponseEntity.ok(new ApiResponse<>(null, "Horario guardado exitosamente", null));
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<List<StudentScheduleSlotDTO>> getSchedule(@PathVariable String studentId) {
        return ResponseEntity.ok(studentScheduleService.getStudentSchedule(studentId));
    }
}
