package com.example.ClinicaMedicala.dto.DoctorDTOComponents;

import com.example.ClinicaMedicala.dto.AppointmentDTOComponents.AppointmentDTO;
import com.example.ClinicaMedicala.entity.DoctorEntityComponents.DoctorMedicalServices;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorMedicalServicesDTO {

    private Integer id_doctor_medical_service;
    private Integer id_doctor;
    private Integer id_medical_service;
    private Double price;
    private Date created_at;
    private Date updated_at;
    private Boolean is_deleted;

    private MedicalServicesDTO medicalService;
    private List<AppointmentDTO> appointments;

    public DoctorMedicalServicesDTO(DoctorMedicalServices doctorMedicalServices) {
        this.id_doctor_medical_service = doctorMedicalServices.getId_doctor_medical_service();
        this.id_doctor = doctorMedicalServices.getDoctor().getId_doctor();
        this.id_medical_service = doctorMedicalServices.getMedicalServices().getId_medical_service();
        this.price = doctorMedicalServices.getPrice();
        this.created_at = doctorMedicalServices.getCreated_at();
        this.updated_at = doctorMedicalServices.getUpdated_at();
        this.is_deleted = doctorMedicalServices.getIs_deleted();

        this.medicalService= new MedicalServicesDTO(doctorMedicalServices.getMedicalServices());

        if(doctorMedicalServices.getAppointment() != null) {
            this.appointments = doctorMedicalServices.getAppointment().stream()
                    .map(AppointmentDTO::new)
                    .collect(Collectors.toList());
        } else {
            this.appointments = new ArrayList<>();
        }
    }
}