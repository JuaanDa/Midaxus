package com.example.Midaxus.model.entities;
import java.time.Duration;
import java.util.Objects;

public class Subject {

        private String idSubject;
        private String nameSubject;
        private String code;
        private int sessionPerWeek;
        private Duration sessionDuration;

        // Getters
        public String getIdSubject() { return idSubject; }
        public String getNameSubject() { return nameSubject; }
        public String getCode() { return code; }
        public int getSessionPerWeek() { return sessionPerWeek; }
        public Duration getSessionDuration() { return sessionDuration; }

        // Setters
        public void setIdSubject(String idSubject) { this.idSubject = idSubject; }
        public void setNameSubject(String nameSubject) { this.nameSubject = nameSubject; }
        public void setCode(String code) { this.code = code; }
        public void setSessionPerWeek(int sessionPerWeek) { this.sessionPerWeek = sessionPerWeek; }
        public void setSessionDuration(Duration sessionDuration) { this.sessionDuration = sessionDuration; }

        @Override
        public String toString() {
            return "Subject{" +
                    "idSubject='" + idSubject + '\'' +
                    ", nameSubject='" + nameSubject + '\'' +
                    ", code='" + code + '\'' +
                    ", sessionPerWeek=" + sessionPerWeek +
                    ", sessionDuration=" + sessionDuration +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Subject that)) return false;
            return Objects.equals(idSubject, that.idSubject);
        }
        @Override
        public int hashCode() { return Objects.hash(idSubject); }
    }

