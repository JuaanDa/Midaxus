package com.example.Midaxus.model.entities;

import java.util.Set;

public class Student extends User {

    private String studentId;
 //   private Set<Enrollment> enrollment;

    public Student() {
    }

    public Student(String studentId, Set<Enrollment> enrollment) {
        this.studentId = studentId;
        this.enrollment = enrollment;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Set<Enrollment> getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(Set<Enrollment> enrollment) {
        this.enrollment = enrollment;
    }
}