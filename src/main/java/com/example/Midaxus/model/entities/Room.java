package com.example.Midaxus.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "room")
public class Room {
    @Id
    private String id;
    
    public Room() {}
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
}
