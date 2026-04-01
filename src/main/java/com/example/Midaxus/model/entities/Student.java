package com.example.Midaxus.model.entities;


import com.example.Midaxus.model.enums.UserType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "student")
@DiscriminatorValue("STUDENT")
public class Student extends User {

    @Column(unique = true)
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