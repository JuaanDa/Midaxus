package com.example.Midaxus.model.dtos;

import java.time.LocalTime;

public class InstitutionPolicyDTO {
    private Long id;
    private LocalTime classStartTime;
    private LocalTime classEndTime;
    private LocalTime lunchStartTime;
    private LocalTime lunchEndTime;
    private Integer standardCapacity;
    private Integer capacityTolerancePercent;
    private Integer maxSessionsPerWeek;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalTime getClassStartTime() {
        return classStartTime;
    }

    public void setClassStartTime(LocalTime classStartTime) {
        this.classStartTime = classStartTime;
    }

    public LocalTime getClassEndTime() {
        return classEndTime;
    }

    public void setClassEndTime(LocalTime classEndTime) {
        this.classEndTime = classEndTime;
    }

    public LocalTime getLunchStartTime() {
        return lunchStartTime;
    }

    public void setLunchStartTime(LocalTime lunchStartTime) {
        this.lunchStartTime = lunchStartTime;
    }

    public LocalTime getLunchEndTime() {
        return lunchEndTime;
    }

    public void setLunchEndTime(LocalTime lunchEndTime) {
        this.lunchEndTime = lunchEndTime;
    }

    public Integer getStandardCapacity() {
        return standardCapacity;
    }

    public void setStandardCapacity(Integer standardCapacity) {
        this.standardCapacity = standardCapacity;
    }

    public Integer getCapacityTolerancePercent() {
        return capacityTolerancePercent;
    }

    public void setCapacityTolerancePercent(Integer capacityTolerancePercent) {
        this.capacityTolerancePercent = capacityTolerancePercent;
    }

    public Integer getMaxSessionsPerWeek() {
        return maxSessionsPerWeek;
    }

    public void setMaxSessionsPerWeek(Integer maxSessionsPerWeek) {
        this.maxSessionsPerWeek = maxSessionsPerWeek;
    }
}
