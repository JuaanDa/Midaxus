package com.example.Midaxus.model.entities;

import java.util.Date;


public class Admin extends User {

    private String adminId;

    public Admin() {}

    public Admin(String adminId, String userName, String firstName,
                 String lastName, String id, String email,
                 String password, Date signInDate) {

        super(userName, firstName, lastName, id, email, password, signInDate);
        this.adminId = adminId;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminId='" + adminId + '\'' +
                '}';
    }
}