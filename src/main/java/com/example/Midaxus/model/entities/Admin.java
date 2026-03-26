package com.example.Midaxus.model.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "admin")
public class Admin extends User {

    private String adminId;

    public Admin() {}

    public Admin(String adminId, String userName, String firstName,
                 String lastName, String id, String email,
                 String password, Date signInDate, String rol) {

        super(userName, firstName, lastName, id, email, password, signInDate, rol);
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