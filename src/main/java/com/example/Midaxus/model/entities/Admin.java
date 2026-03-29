package com.example.Midaxus.model.entities;

import com.example.Midaxus.model.enums.UserType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "admin")
@DiscriminatorValue("ADMIN")
public class Admin extends User {

    @Column(unique = true)
    private String adminId;

    public Admin() {}

    public Admin(String adminId, String userName, String firstName,
                 String lastName, String id, String email,
                 String password, Date signInDate) {

        super(userName, firstName, lastName, id, email, password, signInDate, UserType.ADMIN);
        this.adminId = adminId;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }
}