package com.example.ClinicaMedicala.dto;

import com.example.ClinicaMedicala.entity.Clinic;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClinicDTO {

    private int id_clinic;
    private String clinic_name;
    private String clinic_phone;
    private String clinic_address;
    private Date created_at;
    private Date updated_at;
    private Boolean is_deleted;

    public ClinicDTO(Clinic clinic) {
        this.id_clinic = clinic.getId_clinic();
        this.clinic_name = clinic.getClinic_name();
        this.clinic_phone = clinic.getClinic_phone();
        this.clinic_address = clinic.getClinic_address();
        this.created_at = clinic.getCreated_at();
        this.updated_at = clinic.getUpdated_at();
        this.is_deleted = clinic.getIs_deleted();
    }
}
