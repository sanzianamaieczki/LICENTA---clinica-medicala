package com.example.ClinicaMedicala.service;

import com.example.ClinicaMedicala.dto.DoctorDTOComponents.DoctorDTO;
import com.example.ClinicaMedicala.dto.DoctorDTOComponents.DoctorMedicalServicesDTO;
import com.example.ClinicaMedicala.dto.DoctorDTOComponents.DoctorScheduleDTO;
import com.example.ClinicaMedicala.dto.DoctorDTOComponents.MedicalServicesDTO;
import com.example.ClinicaMedicala.service.DoctorServiceComponents.DoctorComponents;
import com.example.ClinicaMedicala.service.DoctorServiceComponents.DoctorMedicalServicesComponents;
import com.example.ClinicaMedicala.service.DoctorServiceComponents.DoctorScheduleComponents;
import com.example.ClinicaMedicala.service.DoctorServiceComponents.MedicalServicesComponents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DoctorService {
    @Autowired
    private DoctorComponents doctorComponents;
    @Autowired
    private MedicalServicesComponents medicalServicesComponents;
    @Autowired
    private DoctorScheduleComponents doctorScheduleComponents;
    @Autowired
    private DoctorMedicalServicesComponents doctorMedicalServicesComponents;

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

    public DoctorDTO addDoctor(DoctorDTO doctorDTO) {
        return doctorComponents.addDoctor(doctorDTO);
    }

    public DoctorDTO updateDoctor(Integer id_doctor, Map<String, Object> updates) { return doctorComponents.updateDoctor(id_doctor, updates);}

    public void deleteDoctor(Integer id_doctor) {
        doctorComponents.deleteDoctor(id_doctor);
    }


    // ========= Medical Services Methods ===============

    public List<MedicalServicesDTO> getMedicalServicesByFilters(
            Boolean is_deleted,
            String medical_service_name
    ){
        return medicalServicesComponents.getMedicalServicesByFilters(is_deleted, medical_service_name);
    }

    public Optional<MedicalServicesDTO> getMedicalServicesById(int id_medical_service) {
        return medicalServicesComponents.getMedicalServicesById(id_medical_service);
    }

    public MedicalServicesDTO addMedicalService(MedicalServicesDTO medicalServicesDTO) {
        return medicalServicesComponents.addMedicalService(medicalServicesDTO);
    }

    public MedicalServicesDTO updateMedicalService(Integer id_medical_service, Map<String, Object> updates) {
        return medicalServicesComponents.updateMedicalService(id_medical_service, updates);
    }

    public void deleteMedicalService(Integer id_medical_service) {
        medicalServicesComponents.deleteMedicalService(id_medical_service);
    }

    // ========= Doctor Medical Services Methods ===============

    public DoctorMedicalServicesDTO addDoctorMedicalService(DoctorMedicalServicesDTO doctorMedicalServicesDTO){
        return doctorMedicalServicesComponents.addDoctorMedicalService(doctorMedicalServicesDTO);
    }

    public DoctorMedicalServicesDTO updateDoctorMedicalService(Integer id_doctor_medical_service, Integer id_doctor,Map<String, Object> updates){
        return doctorMedicalServicesComponents.updateDoctorMedicalService(id_doctor_medical_service, id_doctor, updates);
    }

    public void deleteDoctorMedicalService(Integer id_doctor_medical_service, Integer id_doctor) {
        doctorMedicalServicesComponents.deleteDoctorMedicalService(id_doctor_medical_service, id_doctor);
    }

    // ========= Doctor Schedule Methods ===============

    public DoctorScheduleDTO addDoctorSchedule(DoctorScheduleDTO doctorScheduleDTO) {
        return doctorScheduleComponents.addDoctorSchedule(doctorScheduleDTO);
    }

    public DoctorScheduleDTO updateDoctorSchedule(Integer id_doctor_schedule,Integer id_doctor, Map<String, Object> updates) {
        return doctorScheduleComponents.updateDoctorSchedule(id_doctor_schedule,id_doctor, updates);
    }

    public void deleteDoctorSchedule(Integer id_doctor_schedule, Integer id_doctor) {
        doctorScheduleComponents.deleteDoctorSchedule(id_doctor_schedule, id_doctor);
    }
}
