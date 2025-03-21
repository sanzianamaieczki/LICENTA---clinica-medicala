package com.example.ClinicaMedicala.repository.AppointmentRepositoryComponents;

import com.example.ClinicaMedicala.entity.AppointmentEntityComponenents.Appointment;
import com.example.ClinicaMedicala.entity.DoctorEntityComponents.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    @Query("SELECT a FROM Appointment a " +
            "WHERE (:is_deleted IS NULL OR a.is_deleted = :is_deleted)" +
            "AND (:appointment_status IS NULL OR LOWER(a.appointment_status) LIKE LOWER(CONCAT('%', :appointment_status, '%')))" +
            "AND (:appointment_date IS NULL OR a.appointment_date >= :appointment_date)")
    List<Appointment> findAppointmentsByFilters(
            @Param("is_deleted") Boolean is_deleted,
            @Param("appointment_status") String appointment_status,
            @Param("appointment_date") Date appointment_date
    );

    @Query("SELECT a FROM Appointment a WHERE a.id_appointment = :id_appointment")
    Optional<Appointment> findAppointmentById(int id_appointment);
//
//    @Query("SELECT a from Appointment a WHERE a.patient.id_patient = :id_patient")
//    List<Appointment> findAppointmentsByPatientId(int id_patient);
//
//    @Query("SELECT a from Appointment a WHERE a.medicalServices.id_medical_service = :id_medical_service")
//    List<Appointment> findAppointmentsByMedicalServiceId(int id_medical_service);
}
