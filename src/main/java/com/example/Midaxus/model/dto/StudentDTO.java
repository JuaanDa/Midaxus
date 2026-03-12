package com.example.Midaxus.model.dto;

import java.util.Set;

public class StudentDTO {

    private String studentId;
    private Set<String> enrollments;

    public StudentDTO() {}

    public StudentDTO(String studentId, Set<String> enrollments) {
        this.studentId = studentId;
        this.enrollments = enrollments;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Set<String> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(Set<String> enrollments) {
        this.enrollments = enrollments;
    }
}