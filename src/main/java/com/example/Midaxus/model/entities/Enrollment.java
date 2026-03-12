package com.example.Midaxus.model.entities;

public class Enrollment {

    private String enrollmentId;
    private Student student;
    private CourseGroup courseGroup;
    private EnrollmentStatus status;

    public Enrollment() {
    }

    public Enrollment(String enrollmentId, Student student, CourseGroup courseGroup, EnrollmentStatus status) {
        this.enrollmentId = enrollmentId;
        this.student = student;
        this.courseGroup = courseGroup;
        this.status = status;
    }

    public String getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(String enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public CourseGroup getCourseGroup() {
        return courseGroup;
    }

    public void setCourseGroup(CourseGroup courseGroup) {
        this.courseGroup = courseGroup;
    }

    public EnrollmentStatus getStatus() {
        return status;
    }

    public void setStatus(EnrollmentStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "enrollmentId='" + enrollmentId + '\'' +
                ", student=" + student +
                ", courseGroup=" + courseGroup +
                ", status=" + status +
                '}';
    }
}