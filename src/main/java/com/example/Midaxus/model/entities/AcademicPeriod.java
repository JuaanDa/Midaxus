package com.example.Midaxus.model.entities;
import java.util.Objects;
public class AcademicPeriod {

        private String code;
        private String name;

        // Getters
        public String getCode() { return code; }
        public String getName() { return name; }

        // Setters
        public void setCode(String code) { this.code = code; }
        public void setName(String name) { this.name = name; }

        @Override
        public String toString() {
            return "AcademicPeriod{" +
                    "code='" + code + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof AcademicPeriod that)) return false;
            return Objects.equals(code, that.code);
        }
        @Override
        public int hashCode() { return Objects.hash(code); }

    }

