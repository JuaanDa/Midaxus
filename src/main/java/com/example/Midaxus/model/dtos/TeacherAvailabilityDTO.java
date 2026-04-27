package com.example.Midaxus.model.dtos;

import java.time.LocalTime;

public class TeacherAvailabilityDTO {
    private String dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;

    public TeacherAvailabilityDTO() {}

    public TeacherAvailabilityDTO(String dayOfWeek, LocalTime startTime, LocalTime endTime) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(String dayOfWeek) { this.dayOfWeek = dayOfWeek; }

    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }

    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }
}
