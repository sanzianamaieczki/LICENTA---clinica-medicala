package com.example.ClinicaMedicala.repository;

import com.example.ClinicaMedicala.entity.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SpecializationRepository extends JpaRepository<Specialization, Integer> {

    @Query("SELECT s FROM Specialization s "+
            "WHERE (:is_deleted IS NULL OR s.is_deleted = :is_deleted) " +
            "AND (:specialization_name IS NULL OR LOWER(s.specialization_name) LIKE LOWER(CONCAT('%', :specialization_name, '%')))")
    List<Specialization> findSpecializationsByFilters(
            @Param("is_deleted") Boolean is_deleted,
            @Param("specialization_name") String specialization_name
    );

    @Query("SELECT s FROM Specialization s WHERE s.id_specialization= :id_specialization")
    Optional<Specialization> findSpecializationById(int id_specialization);

}
