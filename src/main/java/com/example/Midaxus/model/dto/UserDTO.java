package com.example.Midaxus.model.dto;
import java.util.Date;

public class UserDTO {

    private String userName;
    private String id;
    private String email;
    private String password;
    private Date signInDate;

    public UserDTO() {
    }

    public UserDTO(String userName, String id, String email, String password, Date signInDate) {
        this.userName = userName;
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
