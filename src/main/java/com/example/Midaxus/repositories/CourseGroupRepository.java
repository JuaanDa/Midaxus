package com.example.Midaxus.repositories;

import com.example.Midaxus.model.entities.CourseGroup;
import com.example.Midaxus.model.entities.Subject;
import com.example.Midaxus.model.entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseGroupRepository extends JpaRepository<CourseGroup, String> {
    List<CourseGroup> findAllByTeacher(Teacher teacher);
    List<CourseGroup> findAllBySubject(Subject subject);
}
