package com.example.ClinicaMedicala.repository.PatientRepositoryComponents;

import com.example.ClinicaMedicala.entity.PatientEntityComponents.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Integer> {

    @Query("SELECT p FROM Patient p "+
            "WHERE (:is_deleted IS NULL OR p.is_deleted = :is_deleted) " +
            "AND (:first_name IS NULL OR LOWER(p.first_name) LIKE LOWER(CONCAT('%', :first_name, '%')))" +
            "AND (:last_name IS NULL OR LOWER(p.last_name) LIKE LOWER(CONCAT('%', :last_name, '%')))" +
            "AND (:email IS NULL OR LOWER(p.email) LIKE LOWER(CONCAT('%', :email, '%')))" +
            "AND (:phone IS NULL OR LOWER(p.phone) LIKE LOWER(CONCAT('%', :phone, '%')))" +
            "AND (:national_id IS NULL OR LOWER(p.national_id) LIKE LOWER(CONCAT('%', :national_id, '%')))" +
            "AND (:birth_date_start IS NULL AND :birth_date_end IS NULL)" +
            "    OR (:birth_date_start IS NOT NULL AND p.birth_date >= :birth_date_start)" +
            "    OR (:birth_date_end IS NOT NULL AND p.birth_date <= :birth_date_end)"
    )
    List<Patient> findPatientsByFilters(
            @Param("is_deleted") Boolean is_deleted,
            @Param("first_name") String first_name,
            @Param("last_name") String last_name,
            @Param("email") String email,
            @Param("phone") String phone,
            @Param("national_id") String national_id,
            @Param("birth_date_start") Date birth_date_start,
            @Param("birth_date_end") Date birth_date_end
    );

    @Query("SELECT p FROM Patient p WHERE p.id_patient = :id_patient")
    Optional<Patient> findPatientById(int id_patient);

}
