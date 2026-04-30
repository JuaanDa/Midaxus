package com.example.Midaxus.repositories;

import com.example.Midaxus.model.entities.StudentScheduleSlot;
import com.example.Midaxus.model.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentScheduleSlotRepository extends JpaRepository<StudentScheduleSlot, String> {
    List<StudentScheduleSlot> findByStudent(Student student);
    void deleteByStudent(Student student);
}
