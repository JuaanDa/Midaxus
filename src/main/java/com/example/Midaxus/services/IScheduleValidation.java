package com.example.Midaxus.services;

import com.example.Midaxus.model.entities.ScheduleSession;
import java.util.List;

public interface IScheduleValidation {

    List<String> validateNoConsecutiveSessions(List<ScheduleSession> sessions);
}