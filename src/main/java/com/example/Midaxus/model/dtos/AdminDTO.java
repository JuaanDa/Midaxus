package com.example.Midaxus.model.dtos;

public class AdminDTO {

    private String adminId;

    public AdminDTO() {}

    public AdminDTO(String adminId) {
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
        return "AdminDTO{" +
                "adminId='" + adminId + '\'' +
                '}';
    }
}