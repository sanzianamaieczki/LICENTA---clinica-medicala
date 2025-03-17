//package com.example.ClinicaMedicala.service;
//
//import com.example.ClinicaMedicala.dto.AppointmentDTOComponents.AppointmentDTO;
//import com.example.ClinicaMedicala.service.AppointmentServiceComponents.AppointmentComponents;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.*;
//
//@Service
//public class AppointmentService {
//
//    @Autowired
//    AppointmentComponents appointmentComponents;
//
//    public List<AppointmentDTO> getAppointmentByFilters(
//            Boolean is_deleted,
//            Date appointment_date,
//            String appointment_status
//    ){
//        return appointmentComponents.getAppointmentByFilters(is_deleted, appointment_date, appointment_status);
//    }
//
//    public Optional<AppointmentDTO> getAppointmentById(int id_appointment) {
//        return appointmentComponents.getAppointmentById(id_appointment);
//    }
//
//    public List<AppointmentDTO> getAppointmentsByPatient(int id_patient) {
//        return appointmentComponents.getAppointmentsByPatient(id_patient);
//    }
//
//    public List<AppointmentDTO> getAppointmentsByMedicalService(int id_medical_service) {
//        return appointmentComponents.getAppointmentsByMedicalService(id_medical_service);
//    }
//
//    public AppointmentDTO addAppointment(AppointmentDTO appointmentDTO) {
//        return appointmentComponents.addAppointment(appointmentDTO);
//    }
//
//    public AppointmentDTO updateAppointment(Integer id_appointment, Map<String, Object> updates) {
//        return appointmentComponents.updateAppointment(id_appointment, updates);
//    }
//
//    public void deleteAppointment(Integer id_appointment) {
//        appointmentComponents.deleteAppointment(id_appointment);
//    }
//}
