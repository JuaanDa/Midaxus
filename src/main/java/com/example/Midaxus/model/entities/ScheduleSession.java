package com.example.Midaxus.model.entities;
import java.util.Objects;

public class ScheduleSession {

        private String scheduleSessionId;
        private Schedule schedule;                    // asociación 1..* (muchas sesiones pertenecen a un Schedule)
        private CourseGroup courseGroup;              // referencia
        private Room room;                            // referencia (1 a *) desde Room al diagrama
        private VinculationSlot vinculationSlot;      // referencia

        // Getters
        public String getScheduleSessionId() { return scheduleSessionId; }
        public Schedule getSchedule() { return schedule; }
        public CourseGroup getCourseGroup() { return courseGroup; }
        public Room getRoom() { return room; }
        public VinculationSlot getVinculationSlot() { return vinculationSlot; }

        // Setters
        public void setScheduleSessionId(String scheduleSessionId) { this.scheduleSessionId = scheduleSessionId; }

        /** Mantiene la relación bidireccional con Schedule */
        public void setSchedule(Schedule schedule) {
            // si ya está en otro schedule, desvincular
            if (this.schedule != null && this.schedule != schedule) {
                this.schedule.internalRemoveSession(this);
            }
            this.schedule = schedule;
            if (schedule != null) {
                schedule.internalAddSession(this);
            }
        }

        public void setCourseGroup(CourseGroup courseGroup) { this.courseGroup = courseGroup; }
        public void setRoom(Room room) { this.room = room; }
        public void setVinculationSlot(VinculationSlot vinculationSlot) { this.vinculationSlot = vinculationSlot; }

        @Override
        public String toString() {
            return "ScheduleSession{" +
                    "scheduleSessionId='" + scheduleSessionId + '\'' +
                    ", scheduleId=" + (schedule != null ? schedule.getScheduleId() : "null") +
                    ", courseGroup=" + courseGroup +
                    ", room=" + room +
                    ", vinculationSlot=" + vinculationSlot +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ScheduleSession that)) return false;
            return Objects.equals(scheduleSessionId, that.scheduleSessionId);
        }
        @Override
        public int hashCode() { return Objects.hash(scheduleSessionId); }
    }

