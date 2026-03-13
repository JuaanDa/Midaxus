package com.example.Midaxus.model.entities;
import java.util.Objects;
public class CourseGroup {

        private String courseGroupId;  // p.ej. "CS101-G1"
        private String courseCode;     // p.ej. "CS101"
        private String groupLabel;     // p.ej. "G1"

        // Getters
        public String getCourseGroupId() { return courseGroupId; }
        public String getCourseCode() { return courseCode; }
        public String getGroupLabel() { return groupLabel; }

        // Setters
        public void setCourseGroupId(String courseGroupId) { this.courseGroupId = courseGroupId; }
        public void setCourseCode(String courseCode) { this.courseCode = courseCode; }
        public void setGroupLabel(String groupLabel) { this.groupLabel = groupLabel; }

        @Override
        public String toString() {
            return "CourseGroup{" +
                    "courseGroupId='" + courseGroupId + '\'' +
                    ", courseCode='" + courseCode + '\'' +
                    ", groupLabel='" + groupLabel + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof CourseGroup that)) return false;
            return Objects.equals(courseGroupId, that.courseGroupId);
        }
        @Override
        public int hashCode() { return Objects.hash(courseGroupId); }
    }

