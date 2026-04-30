package com.example.Midaxus.repositories;

import com.example.Midaxus.model.entities.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, String> {
}
