package com.example.ClinicaMedicala.dto;

import com.example.ClinicaMedicala.entity.Clinic;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClinicDTO {

    private int id_clinic;
    private String clinic_name;
    private String clinic_phone;
    private String clinic_address;

    public ClinicDTO(Clinic clinic) {
        this.id_clinic = clinic.getId_clinic();
        this.clinic_name = clinic.getClinic_name();
        this.clinic_phone = clinic.getClinic_phone();
        this.clinic_address = clinic.getClinic_address();
    }
}
