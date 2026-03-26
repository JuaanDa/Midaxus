package com.example.Midaxus.repositories;

import com.example.Midaxus.model.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  AdminRepository extends JpaRepository<Admin, String> {
}
