package com.example.Midaxus.model.entities;

import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "institution_policies")
public class InstitutionPolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "class_start_time")
    private LocalTime classStartTime;

    @Column(name = "class_end_time")
    private LocalTime classEndTime;

    @Column(name = "lunch_start_time")
    private LocalTime lunchStartTime;

    @Column(name = "lunch_end_time")
    private LocalTime lunchEndTime;

    @Column(name = "standard_capacity")
    private Integer standardCapacity;

    @Column(name = "capacity_tolerance_percent")
    private Integer capacityTolerancePercent;

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
}
