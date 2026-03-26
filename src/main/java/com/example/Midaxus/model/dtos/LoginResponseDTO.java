package com.example.Midaxus.model.dtos;

public class LoginResponseDTO {
    private boolean success;
    private String message;
    private String role;

    public LoginResponseDTO(boolean success, String message, String role) {
        this.success = success;
        this.message = message;
        this.role = role;
    }

    // Getters y Setters

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}