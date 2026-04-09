package com.example.Midaxus.model.dtos;

public class AdminDTO {

    private String adminId;
    private String userName;
    private String email;

    public AdminDTO() {}

    public AdminDTO(String adminId, String userName, String email) {

        this.adminId = adminId;
        this.userName = userName;
        this.email = email;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
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


    @Override
    public String toString() {
        return "AdminDTO{" +
                "adminId='" + adminId + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}