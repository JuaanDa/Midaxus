package com.example.Midaxus.model.dtos;

import java.util.Date;
import java.util.List;

public class TeacherDTO {

    private String id;
    private String teacherCode;
    private String userName;
    private Date startDate;
    private List<String> subjectsIds;
    private List<TeacherAvailabilityDTO> availabilities;
    private String firstName;
    private String lastName;
    private String password;
    private String email;

    public TeacherDTO() {}

    public TeacherDTO(String id, String teacherCode, String userName,
                      String firstName, String lastName, String email,
                      String password) {
        this.id = id;
        this.teacherCode = teacherCode;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeacherCode() {
        return teacherCode;
    }

    public void setTeacherCode(String teacherCode) {
        this.teacherCode = teacherCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public List<String> getSubjectsIds() {
        return subjectsIds;
    }

    public void setSubjectsIds(List<String> subjectsIds) {
        this.subjectsIds = subjectsIds;
    }

    public List<TeacherAvailabilityDTO> getAvailabilities() {
        return availabilities;
    }

    public void setAvailabilities(List<TeacherAvailabilityDTO> availabilities) {
        this.availabilities = availabilities;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}