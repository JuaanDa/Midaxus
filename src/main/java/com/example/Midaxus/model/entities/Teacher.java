package com.example.Midaxus.model.entities;


import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;


@Entity
@Table(name = "teacher")
@DiscriminatorValue("TEACHER")
public class Teacher extends User {

    private String teacherId;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @ElementCollection
    @CollectionTable(name = "teacher_subjects",
            joinColumns = @JoinColumn(name = "teacher_user_id"))
    @Column(name = "subject")
    private Set<String> subjects;


    public Teacher() {}

    public Teacher(String teacherId, String userName, String firstName,
                   String lastName, String id, String email,
                   String password, Date signInDate,
                   Set<String> subjects, Date startDate, String rol) {

        super(userName, firstName, lastName, id, email, password, signInDate, rol);
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