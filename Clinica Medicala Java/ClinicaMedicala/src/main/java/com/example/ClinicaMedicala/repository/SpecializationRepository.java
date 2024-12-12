package com.example.ClinicaMedicala.repository;

import com.example.ClinicaMedicala.entity.Clinic;
import com.example.ClinicaMedicala.entity.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SpecializationRepository extends JpaRepository<Specialization, Integer> {

    @Query("SELECT s FROM Specialization s WHERE s.is_deleted = false")
    List<Specialization> findAll();

    @Query("SELECT s FROM Specialization s WHERE s.is_deleted = true")
    List<Specialization> findAllDeletedSpecializations();

    @Query("SELECT s FROM Specialization s WHERE s.id_specialization= :id_specialization")
    Optional<Specialization> findSpecializationById(int id_specialization);

    @Query("SELECT s FROM Specialization s where LOWER(s.specialization_name) LIKE LOWER(CONCAT('%', :specialization_name, '%'))")
    List<Specialization> findSpecializationByName(@Param("specialization_name") String specialization_name);

}
