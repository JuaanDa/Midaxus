package com.example.Midaxus.repositories;

import com.example.Midaxus.model.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, String> {
}
