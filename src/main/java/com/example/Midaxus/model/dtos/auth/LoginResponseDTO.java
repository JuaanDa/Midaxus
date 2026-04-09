package com.example.Midaxus.model.dtos.auth;

public class LoginResponseDTO {
    private boolean success;
    private String message;
    private String userType;
    private Object data;

    public LoginResponseDTO(boolean success, String message, String userType, Object data) {
        this.success = success;
        this.message = message;
        this.userType = userType;
        this.data = data;
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

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}