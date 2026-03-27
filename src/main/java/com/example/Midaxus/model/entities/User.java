
package com.example.Midaxus.model.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "user_type")
public abstract class User {

    @Id
    private String id;

    private String userName;
    private String rol;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    private String resetToken;
    private Date resetTokenExpiration;


    @Temporal(TemporalType.DATE)
    private Date signInDate;


    public User() {}

    public User(String userName, String lastName){
        this.userName = userName;
        this.lastName = lastName;
    }

    public User(String userName, String firstName, String lastName,
                String id, String email, String password, Date signInDate, String rol) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.email = email;
        this.password = password;
        this.signInDate = signInDate;
        this.rol = rol;
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

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public Date getResetTokenExpiration() {
        return resetTokenExpiration;
    }

    public void setResetTokenExpiration(Date resetTokenExpiration) {
        this.resetTokenExpiration = resetTokenExpiration;
    }
}