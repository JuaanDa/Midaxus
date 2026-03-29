package com.example.Midaxus.model.dtos;

public class TeacherDTO {

    private String id;
    private String teacherId;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;

    public TeacherDTO() {}

    public TeacherDTO(String id, String teacherId, String userName,
                      String firstName, String lastName, String email) {
        this.id = id;
        this.teacherId = teacherId;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public String getUserName() {
        return userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}