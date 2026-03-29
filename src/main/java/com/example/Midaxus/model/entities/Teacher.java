package com.example.Midaxus.model.entities;


import com.example.Midaxus.model.enums.UserType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;


@Entity
@Table(name = "teacher")
@DiscriminatorValue("TEACHER")
public class Teacher extends User {

    @Column(unique = true)
    private String teacherId;

    @Temporal(TemporalType.DATE)
    private Date startDate;



    public Teacher() {}

    public Teacher(String teacherId, String userName, String firstName,
                   String lastName, String id, String email,
                   String password, Date signInDate,
                   Date startDate, UserType userType) {

        super(userName, firstName, lastName, id, email, password, signInDate, UserType.TEACHER);
        this.teacherId = teacherId;
        this.startDate = startDate;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
}