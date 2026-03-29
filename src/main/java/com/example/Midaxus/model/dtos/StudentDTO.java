package com.example.Midaxus.model.dtos;

public class StudentDTO {

    private String id;
    private String studentId;
    private String userName;
    private String email;

    public StudentDTO() {}

    public StudentDTO(String id, String studentId, String userName, String email ) {

        this.id = id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "StudentDTO{" +
                "id='" + id + '\'' +
                ", studentId='" + studentId + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}