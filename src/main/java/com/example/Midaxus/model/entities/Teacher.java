package com.example.Midaxus.model.entities;

import javax.security.auth.Subject;
import java.util.Date;
import java.util.Set;

public class Teacher extends User {

    private String teacherId;
    private String fullName;
    private Set<Subject> subjects;
    private Date startDate;
    //private Set<CourseGroup> courses;
    //private WorkShift workShift;

    public Teacher() {
    }

    public Teacher(String teacherId, String fullName, Set<Subject> subjects,
                   Date startDate){ //,Set<CourseGroup> courses, WorkShift workShift) {
        this.teacherId = teacherId;
        this.fullName = fullName;
        this.subjects = subjects;
        this.startDate = startDate;
       // this.courses = courses;
        // this.workShift = workShift;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Set<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

  /*  public Set<CourseGroup> getCourses() {
        return courses;
    }

    public void setCourses(Set<CourseGroup> courses) {
        this.courses = courses;
    }

    public WorkShift getWorkShift() {
        return workShift;
    }

    public void setWorkShift(WorkShift workShift) {
        this.workShift = workShift;
    }
   */
}