package com.example.Midaxus.model.entities;


import com.example.Midaxus.model.enums.UserType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "teacher")
@DiscriminatorValue("TEACHER")
public class Teacher extends User {

    @Column(unique = true)
    private String teacherCode;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @OneToMany(mappedBy = "teacher")
    private List<CourseGroup> courseGroups;



    public Teacher() {}

    public Teacher(String teacherCode, String userName, String firstName,
                   String lastName, String id, String email,
                   String password, Date signInDate,
                   Date startDate, UserType userType) {

        super(userName, firstName, lastName, id, email, password, signInDate);
        this.teacherCode = teacherCode;
        this.startDate = startDate;
    }

    public String getTeacherCode() {
        return teacherCode;
    }

    public void setTeacherCode(String teacherCode) {
        this.teacherCode = teacherCode;
    }

    public List<CourseGroup> getCourseGroups() {
        return courseGroups;
    }

    public void setCourseGroups(List<CourseGroup> courseGroups) {
        this.courseGroups = courseGroups;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
}