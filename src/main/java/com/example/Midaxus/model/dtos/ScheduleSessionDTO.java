package com.example.Midaxus.model.dtos;

public class ScheduleSessionDTO {
    private String scheduleSessionId;
    private String scheduleId;
    private String courseGroupId;
    private String roomId;
    private String vinculationSlotId;
    
    // Additional fields for convenience if needed
    private String courseCode;
    private String courseGroup;
    private String day;
    private String slot;
    private String teacher;

    public ScheduleSessionDTO() {}

    public String getScheduleSessionId() { return scheduleSessionId; }
    public void setScheduleSessionId(String scheduleSessionId) { this.scheduleSessionId = scheduleSessionId; }

    public String getScheduleId() { return scheduleId; }
    public void setScheduleId(String scheduleId) { this.scheduleId = scheduleId; }

    public String getCourseGroupId() { return courseGroupId; }
    public void setCourseGroupId(String courseGroupId) { this.courseGroupId = courseGroupId; }

    public String getRoomId() { return roomId; }
    public void setRoomId(String roomId) { this.roomId = roomId; }

    public String getVinculationSlotId() { return vinculationSlotId; }
    public void setVinculationSlotId(String vinculationSlotId) { this.vinculationSlotId = vinculationSlotId; }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public String getCourseGroup() { return courseGroup; }
    public void setCourseGroup(String courseGroup) { this.courseGroup = courseGroup; }

    public String getDay() { return day; }
    public void setDay(String day) { this.day = day; }

    public String getSlot() { return slot; }
    public void setSlot(String slot) { this.slot = slot; }

    public String getTeacher() { return teacher; }
    public void setTeacher(String teacher) { this.teacher = teacher; }
}
