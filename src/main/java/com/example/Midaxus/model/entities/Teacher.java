package com.example.Midaxus.model.entities;


import java.util.Date;
import java.util.Set;


public class Teacher extends User {


    private String teacherId;

    private Set<String> subjects;

    private Date startDate;

    public Teacher() {}

    public Teacher(String teacherId, String userName, String firstName,
                   String lastName, String id, String email,
                   String password, Date signInDate,
                   Set<String> subjects, Date startDate) {

        super(userName, firstName, lastName, id, email, password, signInDate);
        this.teacherId = teacherId;
        this.subjects = subjects;
        this.startDate = startDate;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public Set<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<String> subjects) {
        this.subjects = subjects;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
}