package com.example.Midaxus.model.dtos;

public class StudentDTO {

    private String studentId;
    private String userName;
    private String email;

    public StudentDTO() {}

    public StudentDTO(String studentId, String userName, String email ) {
        this.studentId = studentId;
        this.userName = userName;
        this.email = email;
    }


    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}