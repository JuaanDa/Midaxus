package com.example.Midaxus.model.entities;

public class Admin extends User {

    private String adminId;

    public Admin() {
    }

    public Admin(String adminId) {
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
