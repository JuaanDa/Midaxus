package com.example.Midaxus.repositories;

import com.example.Midaxus.model.entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, String> {
    Optional<Teacher> findByTeacherCode(String teacherCode);
}
