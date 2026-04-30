package com.example.Midaxus.services;

import com.example.Midaxus.model.dtos.ScheduleSessionDTO;
import com.example.Midaxus.model.entities.ScheduleSession;

import java.util.List;

public interface IScheduleSessionService {
    ScheduleSession saveSession(ScheduleSessionDTO dto);
    List<ScheduleSession> getSessionsByCourse(String courseGroupId);
}
