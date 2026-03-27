package com.example.Midaxus.model.entities;


import java.util.Date;

/**
 * Representa un estudiante dentro del sistema.
 */

public class Student extends User {

    private String studentId;

    public Student() {
    }

    public Student(String studentId, String userName, String firstName,
                   String lastName, String id, String email,
                   String password, Date signInDate) {

        super(userName, firstName, lastName, id, email, password, signInDate);
        this.studentId = studentId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}