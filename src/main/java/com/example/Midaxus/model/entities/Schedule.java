package com.example.Midaxus.model.entities;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class Schedule {

        private String scheduleId;
        private AcademicPeriod academicPeriod;
        private String scheduleStatus;           // Mantengo String como indica el diagrama
        private String version;
        private LocalDateTime generatedAt;
        private LocalDateTime publishedAt;
        private Set<ScheduleSession> sessions = new LinkedHashSet<>();

        // Getters
        public String getScheduleId() { return scheduleId; }
        public AcademicPeriod getAcademicPeriod() { return academicPeriod; }
        public String getScheduleStatus() { return scheduleStatus; }
        public String getVersion() { return version; }
        public LocalDateTime getGeneratedAt() { return generatedAt; }
        public LocalDateTime getPublishedAt() { return publishedAt; }
        public Set<ScheduleSession> getSessions() { return sessions; }

        // Setters
        public void setScheduleId(String scheduleId) { this.scheduleId = scheduleId; }
        public void setAcademicPeriod(AcademicPeriod academicPeriod) { this.academicPeriod = academicPeriod; }
        public void setScheduleStatus(String scheduleStatus) { this.scheduleStatus = scheduleStatus; }
        public void setVersion(String version) { this.version = version; }
        public void setGeneratedAt(LocalDateTime generatedAt) { this.generatedAt = generatedAt; }
        public void setPublishedAt(LocalDateTime publishedAt) { this.publishedAt = publishedAt; }

        /** Reemplaza el conjunto completo de sesiones manteniendo la bidireccionalidad */
        public void setSessions(Set<ScheduleSession> newSessions) {
            // Desvincular las actuales
            for (ScheduleSession s : new LinkedHashSet<>(sessions)) {
                s.setSchedule(null);
            }
            this.sessions.clear();
            if (newSessions != null) {
                for (ScheduleSession s : newSessions) {
                    addSession(s);
                }
            }
        }

        // Métodos de conveniencia para manejar la asociación 1..* (Schedule -> ScheduleSession)
        public void addSession(ScheduleSession session) {
            if (session == null) return;
            session.setSchedule(this); // esto llamará a internalAddSession
        }
        public void removeSession(ScheduleSession session) {
            if (session == null) return;
            if (sessions.contains(session)) {
                session.setSchedule(null); // esto llamará a internalRemoveSession
            }
        }

        /** Solo uso interno para evitar recursión en setSchedule */
        void internalAddSession(ScheduleSession session) { this.sessions.add(session); }
        void internalRemoveSession(ScheduleSession session) { this.sessions.remove(session); }

        @Override
        public String toString() {
            return "Schedule{" +
                    "scheduleId='" + scheduleId + '\'' +
                    ", academicPeriod=" + academicPeriod +
                    ", scheduleStatus='" + scheduleStatus + '\'' +
                    ", version='" + version + '\'' +
                    ", generatedAt=" + generatedAt +
                    ", publishedAt=" + publishedAt +
                    ", sessionsCount=" + sessions.size() +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Schedule that)) return false;
            return Objects.equals(scheduleId, that.scheduleId);
        }
        @Override
        public int hashCode() { return Objects.hash(scheduleId); }
    }

