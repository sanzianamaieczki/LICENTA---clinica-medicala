package com.example.ClinicaMedicala.repository.DoctorRepositoryComponents;

import com.example.ClinicaMedicala.entity.DoctorEntityComponents.DoctorMedicalServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DoctorMedicalServicesRepository extends JpaRepository<DoctorMedicalServices, Integer> {

    @Query("SELECT dms FROM DoctorMedicalServices dms WHERE dms.id_doctor_medical_service = :id_doctor_medical_service")
    Optional<DoctorMedicalServices> findDoctorMedicalServicesById(int id_doctor_medical_service);
}
