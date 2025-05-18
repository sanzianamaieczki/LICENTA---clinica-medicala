package com.example.ClinicaMedicala.service;

import com.example.ClinicaMedicala.dto.AppointmentDTOComponents.AppointmentDTO;
import com.example.ClinicaMedicala.dto.AppointmentDTOComponents.MedicalLetterDTO;
import com.example.ClinicaMedicala.service.AppointmentServiceComponents.AppointmentComponent;
import com.example.ClinicaMedicala.service.AppointmentServiceComponents.MedicalLetterComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentComponent appointmentComponent;
    @Autowired
    private MedicalLetterComponent medicalLetterComponent;

    // ========= Appointment Methods ===============

    public List<AppointmentDTO> getAppointmentsByFilters(
            Boolean is_deleted,
            String appointment_status,
            Date appointment_date
    ){
        return appointmentComponent.getAppointmentsByFilters(is_deleted, appointment_status, appointment_date);
    }
    public Optional<AppointmentDTO> getAppointmentById(int id_appointment) {
        return appointmentComponent.getAppointmentById(id_appointment);
    }

    public List<AppointmentDTO> getAppointmentsByDoctorId(int id_doctor) {
        return appointmentComponent.getAppointmentsByDoctorId(id_doctor);
    }

    public AppointmentDTO addAppointment(AppointmentDTO appointmentDTO) {
        return appointmentComponent.addAppointment(appointmentDTO);
    }

    public AppointmentDTO updateAppointment(Integer id_appointment, Map<String, Object> updates) {
        return appointmentComponent.updateAppointment(id_appointment, updates);
    }

    public void deleteAppointment(Integer id_appointment) {
        appointmentComponent.deleteAppointment(id_appointment);
    }

    // ========= Medical Letter Methods ===============

    public MedicalLetterDTO addMedicalLetter(MedicalLetterDTO medicalLetterDTO) {
        return medicalLetterComponent.addMedicalLetter(medicalLetterDTO);
    }

    public MedicalLetterDTO updateMedicalLetter(Integer id_appointment, Integer id_medical_letter, Map<String, Object> updates) {
        return medicalLetterComponent.updateMedicalLetter(id_appointment,id_medical_letter, updates);
    }

    public void deleteMedicalLetter(Integer id_appointment, Integer id_medical_letter) {
        medicalLetterComponent.deleteMedicalLetter(id_appointment, id_medical_letter);
    }
}
