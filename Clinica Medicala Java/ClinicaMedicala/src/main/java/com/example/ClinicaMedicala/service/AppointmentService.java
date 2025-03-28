package com.example.ClinicaMedicala.service;

import com.example.ClinicaMedicala.dto.AppointmentDTOComponents.AppointmentDTO;
import com.example.ClinicaMedicala.entity.AppointmentEntityComponenents.Appointment;
import com.example.ClinicaMedicala.entity.DoctorEntityComponents.Doctor;
import com.example.ClinicaMedicala.entity.DoctorEntityComponents.DoctorMedicalServices;
import com.example.ClinicaMedicala.entity.PatientEntityComponents.Patient;
import com.example.ClinicaMedicala.enums.AppointmentStatus;
import com.example.ClinicaMedicala.repository.AppointmentRepositoryComponents.AppointmentRepository;
import com.example.ClinicaMedicala.repository.DoctorRepositoryComponents.DoctorRepository;
import com.example.ClinicaMedicala.repository.PatientRepositoryComponents.PatientRepository;
import com.example.ClinicaMedicala.service.AppointmentServiceComponents.AppointmentComponent;
import com.example.ClinicaMedicala.utils.AppointmentValidator;
import com.example.ClinicaMedicala.utils.CheckFields;
import com.example.ClinicaMedicala.utils.DTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentComponent appointmentComponent;

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

    public AppointmentDTO addAppointment(AppointmentDTO appointmentDTO) {
        return appointmentComponent.addAppointment(appointmentDTO);
    }

    public AppointmentDTO updateAppointment(Integer id_appointment, Map<String, Object> updates) {
        return appointmentComponent.updateAppointment(id_appointment, updates);
    }

    public void deleteAppointment(Integer id_appointment) {
        appointmentComponent.deleteAppointment(id_appointment);
    }
}
