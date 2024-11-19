package com.example.ClinicaMedicala.repository;

import com.example.ClinicaMedicala.entity.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClinicRepository extends JpaRepository<Clinic, Integer> {
    List<Clinic> findAll();
}
