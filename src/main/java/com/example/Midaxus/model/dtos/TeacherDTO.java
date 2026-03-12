package com.example.Midaxus.model.dtos;

public class TeacherDTO {
    private String teacherId;
    private String firstName;
    private String lastName;
    //private Set<CourseGroup> courses;
    //private WorkShift workShift;

    public TeacherDTO() {
    }

    public TeacherDTO(String teacherId, String firstName,String lastName){ //,Set<CourseGroup> courses, WorkShift workShift) {
        this.teacherId = teacherId;
        this.firstName = firstName;
        this.lastName = lastName;
        // this.courses = courses;
        // this.workShift = workShift;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
