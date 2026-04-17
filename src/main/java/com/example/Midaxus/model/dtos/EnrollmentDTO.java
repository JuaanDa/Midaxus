package com.example.Midaxus.model.dtos;

import com.example.Midaxus.model.entities.CourseGroup;
import com.example.Midaxus.model.entities.Student;
import com.example.Midaxus.model.enums.EnrollmentStatus;

public class EnrollmentDTO {

    private String enrollmentId;
    private String studentId;
    private String courseGroupId;
    private EnrollmentStatus status;

    public EnrollmentDTO() {}

    public EnrollmentDTO(String enrollmentId, String studentId,
                         String courseGroupId, EnrollmentStatus status) {
        this.enrollmentId = enrollmentId;
        this.studentId = studentId;
        this.courseGroupId = courseGroupId;
        this.status = status;


    // getters & setters
}

    public String getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(String enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCourseGroupId() {
        return courseGroupId;
    }

    public void setCourseGroupId(String courseGroupId) {
        this.courseGroupId = courseGroupId;
    }

    public EnrollmentStatus getStatus() {
        return status;
    }

    public void setStatus(EnrollmentStatus status) {
        this.status = status;
    }
}
