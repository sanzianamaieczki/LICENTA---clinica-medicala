package com.example.ClinicaMedicala.repository.DoctorRepositoryComponents;

import com.example.ClinicaMedicala.entity.DoctorEntityComponents.MedicalServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MedicalServicesRepository extends JpaRepository<MedicalServices, Integer> {

    @Query("SELECT ms FROM MedicalServices ms "+
            "WHERE (:is_deleted IS NULL OR ms.is_deleted = :is_deleted) " +
            "AND (:medical_service_name IS NULL OR LOWER(ms.medical_service_name) LIKE LOWER(CONCAT('%', :medical_service_name, '%')))" +
            "AND (:medical_service_type IS NULL OR LOWER(ms.medical_service_type) LIKE LOWER(CONCAT('%', :medical_service_type, '%')))"
            )
    List<MedicalServices> findMedicalServicesByFilters(
            @Param("is_deleted") Boolean is_deleted,
            @Param("medical_service_name") String medical_service_name,
            @Param("medical_service_type") String medical_service_type
    );

    @Query("SELECT ms FROM MedicalServices ms WHERE ms.id_medical_service = :id_medical_service")
    Optional<MedicalServices> findMedicalServicesById(int id_medical_service);
}
