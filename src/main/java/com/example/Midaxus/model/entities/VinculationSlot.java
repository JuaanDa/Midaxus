package com.example.Midaxus.model.entities;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;

public class VinculationSlot {
    private String vinculationSlotId;
    private DayOfWeek day;
    private LocalTime startTime;
    private LocalTime endTime;

    // Getters
    public String getVinculationSlotId() { return vinculationSlotId; }
    public DayOfWeek getDay() { return day; }
    public LocalTime getStartTime() { return startTime; }
    public LocalTime getEndTime() { return endTime; }

    // Setters
    public void setVinculationSlotId(String vinculationSlotId) { this.vinculationSlotId = vinculationSlotId; }
    public void setDay(DayOfWeek day) { this.day = day; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }

    @Override
    public String toString() {
        return "VinculationSlot{" +
                "vinculationSlotId='" + vinculationSlotId + '\'' +
                ", day=" + day +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }

    // Opcional (útil para Set/Map)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VinculationSlot that)) return false;
        return Objects.equals(vinculationSlotId, that.vinculationSlotId);
    }
    @Override
    public int hashCode() { return Objects.hash(vinculationSlotId); }
}