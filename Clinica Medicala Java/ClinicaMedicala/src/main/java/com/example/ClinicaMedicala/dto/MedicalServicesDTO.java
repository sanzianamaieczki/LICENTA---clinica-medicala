package com.example.ClinicaMedicala.dto;

import com.example.ClinicaMedicala.entity.MedicalServices;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicalServicesDTO {

    private Integer id_medical_service;
    private int id_doctor;
    private String medical_service_name;
    private Double price;
    private String medical_service_type;
    private Date created_at;
    private Date updated_at;
    private Boolean is_deleted;

    public MedicalServicesDTO(MedicalServices medicalServices) {
        this.id_medical_service = medicalServices.getId_medical_service();
        this.id_doctor = medicalServices.getDoctor().getId_doctor();
        this.medical_service_name = medicalServices.getMedical_service_name();
        this.price = medicalServices.getPrice();
        this.medical_service_type = medicalServices.getMedical_service_type().name();
        this.created_at = medicalServices.getCreated_at();
        this.updated_at = medicalServices.getUpdated_at();
        this.is_deleted = medicalServices.getIs_deleted();
    }
}