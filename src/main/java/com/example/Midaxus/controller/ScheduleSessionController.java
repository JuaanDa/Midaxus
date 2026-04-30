package com.example.Midaxus.controller;

import com.example.Midaxus.model.dtos.ApiResponse;
import com.example.Midaxus.model.dtos.ScheduleSessionDTO;
import com.example.Midaxus.model.entities.ScheduleSession;
import com.example.Midaxus.services.IScheduleSessionService;
import com.example.Midaxus.services.IScheduleValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedule-sessions")
public class ScheduleSessionController {

    @Autowired
    private IScheduleSessionService scheduleSessionService;

    @Autowired
    private IScheduleValidation scheduleValidationService;

    @PostMapping
    public ResponseEntity<ApiResponse<ScheduleSessionDTO>> createSession(@RequestBody ScheduleSessionDTO dto) {
        
        // 1. Save session normally (persisted in DB)
        ScheduleSession savedSession = scheduleSessionService.saveSession(dto);
        
        // 2. Get all sessions for this course to validate the business rule
        List<ScheduleSession> courseSessions = scheduleSessionService.getSessionsByCourse(dto.getCourseGroupId());
        
        // 3. Invoke ScheduleValidationService
        List<String> warnings = scheduleValidationService.validateNoConsecutiveSessions(courseSessions);
        
        // 4. Return DTO with success message and any soft warnings
        // For simplicity, we just return the same DTO we received, but updated with the ID
        dto.setScheduleSessionId(savedSession.getScheduleSessionId());
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(dto, "Sesión creada exitosamente", warnings));
    }
}
