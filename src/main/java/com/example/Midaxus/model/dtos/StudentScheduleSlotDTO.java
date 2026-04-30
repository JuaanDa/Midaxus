package com.example.Midaxus.model.dtos;

public class StudentScheduleSlotDTO {
    private String courseGroupId;
    private String courseCode;
    private String subjectName;
    private String day;
    private String slot;

    public StudentScheduleSlotDTO() {}

    public String getCourseGroupId() { return courseGroupId; }
    public void setCourseGroupId(String courseGroupId) { this.courseGroupId = courseGroupId; }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }

    public String getDay() { return day; }
    public void setDay(String day) { this.day = day; }

    public String getSlot() { return slot; }
    public void setSlot(String slot) { this.slot = slot; }
}
