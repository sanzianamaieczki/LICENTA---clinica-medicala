package com.example.ClinicaMedicala.dto;

import com.example.ClinicaMedicala.entity.Doctor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDTO {

    private Integer id_doctor;
    private String last_name;
    private String first_name;
    private String email;
    private String phone;
    private Integer id_specialization;
    private Integer id_clinic;
    private Date created_at;
    private Date updated_at;
    private Boolean is_deleted;

    public DoctorDTO(Doctor doctor) {
        this.id_doctor = doctor.getId_doctor();
        this.last_name = doctor.getLast_name();
        this.first_name = doctor.getFirst_name();
        this.email = doctor.getEmail();
        this.phone = doctor.getPhone();
        this.id_specialization = doctor.getSpecialization().getId_specialization();
        this.id_clinic = doctor.getClinic().getId_clinic();
        this.created_at = doctor.getCreated_at();
        this.updated_at = doctor.getUpdated_at();
        this.is_deleted = doctor.getIs_deleted();
    }
}