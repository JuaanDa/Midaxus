package com.example.Midaxus.model.dtos;

public class CourseGroupDTO {

    private String courseGroupId;
    private String teacherId;
    private String subjectId;
    private String academicPeriodId;
    private String code;
    private int capacity;

    public CourseGroupDTO() {}

    public CourseGroupDTO(String courseGroupId, String teacherId, String subjectId,
                          String academicPeriodId, String code, int capacity) {
        this.courseGroupId = courseGroupId;
        this.teacherId = teacherId;
        this.subjectId = subjectId;
        this.academicPeriodId = academicPeriodId;
        this.code = code;
        this.capacity = capacity;
    }

    public String getCourseGroupId() {
        return courseGroupId;
    }

    public void setCourseGroupId(String courseGroupId) {
        this.courseGroupId = courseGroupId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getAcademicPeriodId() {
        return academicPeriodId;
    }

    public void setAcademicPeriodId(String academicPeriodId) {
        this.academicPeriodId = academicPeriodId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}