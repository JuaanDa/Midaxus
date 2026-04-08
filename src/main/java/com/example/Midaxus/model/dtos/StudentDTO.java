package com.example.Midaxus.model.dtos;

import java.util.Date;

public class StudentDTO {


    private String studentId;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String password;
    private Date signInDate;

    public StudentDTO() {}

    public StudentDTO( String studentId, String firstName, String lastName,
                       String userName, String email, String password, Date signInDate ) {


        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.signInDate = signInDate;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getSignInDate() {
        return signInDate;
    }

    public void setSignInDate(Date signInDate) {
        this.signInDate = signInDate;
    }


}