package com.example.Midaxus.repositories;

import com.example.Midaxus.model.entities.CourseGroup;
import com.example.Midaxus.model.entities.Enrollment;
import com.example.Midaxus.model.entities.Student;
import com.example.Midaxus.model.enums.EnrollmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, String> {
    List<Enrollment> getAllByCourseGroup(CourseGroup courseGroup);

    List<Enrollment> findAllByStatus(EnrollmentStatus status);
    boolean existsByStudentAndCourseGroup(Student student, CourseGroup courseGroup);

}
