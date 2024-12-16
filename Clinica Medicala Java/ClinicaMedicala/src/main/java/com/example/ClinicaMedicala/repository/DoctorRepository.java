package com.example.ClinicaMedicala.repository;

import com.example.ClinicaMedicala.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

    @Query("SELECT d FROM Doctor d "+
            "WHERE (:is_deleted IS NULL OR d.is_deleted = :is_deleted) " +
            "AND (:first_name IS NULL OR LOWER(d.first_name) LIKE LOWER(CONCAT('%', :first_name, '%')))" +
            "AND (:last_name IS NULL OR LOWER(d.last_name) LIKE LOWER(CONCAT('%', :last_name, '%')))" +
            "AND (:email IS NULL OR LOWER(d.email) LIKE LOWER(CONCAT('%', :email, '%')))"
    )
    List<Doctor> findDoctorsByFilters(
            @Param("is_deleted") Boolean is_deleted,
            @Param("first_name") String first_name,
            @Param("last_name") String last_name,
            @Param("email") String email
    );

    @Query("SELECT d FROM Doctor d WHERE d.id_doctor = :id_doctor")
    Optional<Doctor> findDoctorById(int id_doctor);

    @Query("SELECT d FROM Doctor d WHERE d.specialization.id_specialization = :id_specialization")
    List<Doctor> findDoctorsBySpecializationId(int id_specialization);

    @Query("SELECT d from Doctor d WHERE d.clinic.id_clinic = :id_clinic")
    List<Doctor> findDoctorsByClinicId(int id_clinic);
}
