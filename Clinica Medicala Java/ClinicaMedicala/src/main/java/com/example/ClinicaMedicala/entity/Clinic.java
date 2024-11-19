package com.example.ClinicaMedicala.entity;

import com.example.ClinicaMedicala.dto.ClinicDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "clinics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Clinic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_clinic;

    @Column(unique = true, nullable = false)
    private String clinic_name;

    @Column(nullable = false)
    private String clinic_phone;

    @Column(nullable = false)
    private String clinic_address;

    public Clinic(ClinicDTO clinicDTO) {
        this.clinic_name = clinicDTO.getClinic_name();
        this.clinic_phone = clinicDTO.getClinic_phone();
        this.clinic_address = clinicDTO.getClinic_address();
    }
}
