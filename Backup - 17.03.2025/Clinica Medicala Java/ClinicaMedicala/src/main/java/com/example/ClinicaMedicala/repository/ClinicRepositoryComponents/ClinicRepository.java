package com.example.ClinicaMedicala.repository.ClinicRepositoryComponents;

import com.example.ClinicaMedicala.entity.ClinicEntityComponents.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClinicRepository extends JpaRepository<Clinic, Integer> {

    @Query("SELECT c FROM Clinic c "+
            "WHERE (:is_deleted IS NULL OR c.is_deleted = :is_deleted) " +
            "AND (:clinic_name IS NULL OR LOWER(c.clinic_name) LIKE LOWER(CONCAT('%', :clinic_name, '%')))" +
            "AND (:clinic_address IS NULL OR LOWER(c.clinic_address) LIKE LOWER(CONCAT('%', :clinic_address, '%')))" +
            "AND (:clinic_phone IS NULL OR LOWER(c.clinic_phone) LIKE LOWER(CONCAT('%', :clinic_phone, '%')))"
    )
    List<Clinic> findClinicsByFilters(
            @Param("is_deleted") Boolean is_deleted,
            @Param("clinic_name") String clinic_name,
            @Param("clinic_address") String clinic_address,
            @Param("clinic_phone") String clinic_phone
    );

    @Query("SELECT c FROM Clinic c WHERE c.id_clinic = :id_clinic")
    Optional<Clinic> findClinicById(int id_clinic);
}
