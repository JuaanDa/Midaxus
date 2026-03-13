package com.example.Midaxus.model.entities;

import java.util.Date;


public abstract class User {

    private String userName;
    private String firstName;
    private String lastName;
    private String id;
    private String email;
    private String password;
    private Date signInDate;

    public User() {}

    public User(String userName, String lastName){
        this.userName = userName;
        this.lastName = lastName;
    }

    public User(String userName, String firstName, String lastName,
                String id, String email, String password, Date signInDate) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.email = email;
        this.password = password;
        this.signInDate = signInDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Date getSignInDate() {
        return signInDate;
    }

    public void setSignInDate(Date signInDate) {
        this.signInDate = signInDate;
    }
}