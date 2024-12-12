package com.example.ClinicaMedicala.repository;

import com.example.ClinicaMedicala.entity.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClinicRepository extends JpaRepository<Clinic, Integer> {

    @Query("SELECT c FROM Clinic c WHERE c.is_deleted = false")
    List<Clinic> findAll();

    @Query("SELECT c FROM Clinic c WHERE c.is_deleted = true")
    List<Clinic> findAllDeletedClinics();

    @Query("SELECT c FROM Clinic c WHERE c.id_clinic = :id_clinic")
    Optional<Clinic> findClinicById(int id_clinic);

    @Query("SELECT c FROM Clinic c where LOWER(c.clinic_name) LIKE LOWER(CONCAT('%', :clinic_name, '%'))")
    List<Clinic> findClinicByName(@Param("clinic_name") String clinic_name);

    @Query("SELECT c FROM Clinic c where LOWER(c.clinic_address) LIKE LOWER(CONCAT('%', :clinic_address, '%'))")
    List<Clinic> findClinicByAddress(@Param("clinic_address") String clinic_address);
}
