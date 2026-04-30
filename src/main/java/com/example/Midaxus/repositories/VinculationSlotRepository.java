package com.example.Midaxus.repositories;

import com.example.Midaxus.model.entities.VinculationSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VinculationSlotRepository extends JpaRepository<VinculationSlot, String> {
}
