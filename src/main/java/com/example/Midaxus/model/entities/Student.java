package com.example.Midaxus.model.entities;


import jakarta.persistence.*;

import java.util.Date;

/**
 * Representa un estudiante dentro del sistema.
 */

@Entity
@Table(name = "student")
@DiscriminatorValue("STUDENT")
public class Student extends User {

    private String studentId;

    public Student() {
    }

    public Student(String studentId, String userName, String firstName,
                   String lastName, String id, String email,
                   String password, Date signInDate, String rol) {

        super(userName, firstName, lastName, id, email, password, signInDate, rol);
        this.studentId = studentId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}