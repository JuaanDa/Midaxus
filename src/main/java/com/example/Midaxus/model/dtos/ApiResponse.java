package com.example.Midaxus.model.dtos;

import java.util.List;

public class ApiResponse<T> {
    private T data;
    private String message;
    private List<String> warnings;

    public ApiResponse() {
    }

    public ApiResponse(T data, String message, List<String> warnings) {
        this.data = data;
        this.message = message;
        this.warnings = warnings;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<String> warnings) {
        this.warnings = warnings;
    }
}
