package com.example.ClinicaMedicala.service;

import com.example.ClinicaMedicala.dto.DoctorDTOComponents.DoctorDTO;
import com.example.ClinicaMedicala.dto.DoctorDTOComponents.DoctorScheduleDTO;
import com.example.ClinicaMedicala.dto.DoctorDTOComponents.MedicalServicesDTO;
import com.example.ClinicaMedicala.service.DoctorServiceComponents.DoctorComponents;
//import com.example.ClinicaMedicala.service.DoctorServiceComponents.DoctorScheduleComponents;
import com.example.ClinicaMedicala.service.DoctorServiceComponents.MedicalServicesComponents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.*;

@Service
public class DoctorService {
    @Autowired
    private DoctorComponents doctorComponents;
//    @Autowired
//    private DoctorScheduleComponents doctorScheduleComponents;
    @Autowired
    private MedicalServicesComponents medicalServicesComponents;

    // ========= Doctor Methods ===============

    public List<DoctorDTO> getDoctorsByFilters(
            Boolean is_deleted,
            String first_name,
            String last_name,
            String email,
            String phone
    ){
        return doctorComponents.getDoctorsByFilters(is_deleted, first_name, last_name, email, phone);
    }

    public Optional<DoctorDTO> getDoctorById(int id_doctor) {
        return doctorComponents.getDoctorById(id_doctor);
    }

    public List<DoctorDTO> getDoctorsBySpecialization(int id_specialization) {
        return doctorComponents.getDoctorsBySpecialization(id_specialization);
    }

    public List<DoctorDTO> getDoctorsByClinic(int id_clinic) {
        return doctorComponents.getDoctorsByClinic(id_clinic);
    }

    public DoctorDTO addDoctor(DoctorDTO doctorDTO) {
        return doctorComponents.addDoctor(doctorDTO);
    }

    public DoctorDTO updateDoctor(Integer id_doctor, Map<String, Object> updates) {
        return doctorComponents.updateDoctor(id_doctor, updates);
    }

    public void deleteDoctor(Integer id_doctor) {
        doctorComponents.deleteDoctor(id_doctor);
    }


    // Medical Services

    public List<MedicalServicesDTO> getMedicalServicesByFilters(
            Boolean is_deleted,
            String medical_service_name
    ){
        return medicalServicesComponents.getMedicalServicesByFilters(is_deleted, medical_service_name);
    }

    public Optional<MedicalServicesDTO> getMedicalServicesById(int id_medical_service) {
        return medicalServicesComponents.getMedicalServicesById(id_medical_service);
    }

//    public List<MedicalServicesDTO> getMedicalServicesByDoctor(int id_doctor) {
//        return medicalServicesComponents.getMedicalServicesByDoctor(id_doctor);
//    }

    public MedicalServicesDTO addMedicalService(MedicalServicesDTO medicalServicesDTO) {
        return medicalServicesComponents.addMedicalService(medicalServicesDTO);
    }

    public MedicalServicesDTO updateMedicalService(Integer id_medical_service, Map<String, Object> updates) {
        return medicalServicesComponents.updateMedicalService(id_medical_service, updates);
    }

    public void deleteMedicalService(Integer id_medical_service) {
        medicalServicesComponents.deleteMedicalService(id_medical_service);
    }


    // Doctor Schedule
//
//    public List<DoctorScheduleDTO> getDoctorScheduleByFilters(
//            Boolean is_deleted,
//            String day_of_week,
//            Time start_time,
//            Time end_time
//
//    ){
//        return doctorScheduleComponents.getDoctorScheduleByFilters(is_deleted, day_of_week, start_time, end_time);
//    }
//
//    public Optional<DoctorScheduleDTO> getDoctorScheduleById(int id_doctor_schedule) {
//        return doctorScheduleComponents.getDoctorScheduleById(id_doctor_schedule);
//    }
//
//    public List<DoctorScheduleDTO> getDoctorScheduleByDoctor(int id_doctor) {
//        return doctorScheduleComponents.getDoctorScheduleByDoctor(id_doctor);
//    }
//
//    public DoctorScheduleDTO addDoctorSchedule(DoctorScheduleDTO doctorScheduleDTO) {
//        return doctorScheduleComponents.addDoctorSchedule(doctorScheduleDTO);
//    }
//
//    public DoctorScheduleDTO updateDoctorSchedule(Integer id_doctor_schedule, Map<String, Object> updates) {
//        return doctorScheduleComponents.updateDoctorSchedule(id_doctor_schedule, updates);
//    }
//
//    public void deleteDoctorSchedule(Integer id_doctor_schedule) {
//        doctorScheduleComponents.deleteDoctorSchedule(id_doctor_schedule);
//    }
}
