package com.example.Midaxus.repositories;

import com.example.Midaxus.model.entities.InstitutionPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstitutionPolicyRepository extends JpaRepository<InstitutionPolicy, Long> {
}
