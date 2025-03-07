package com.example.ClinicaMedicala.dto.PatientDTOComponents;

import com.example.ClinicaMedicala.entity.PatientEntityComponents.Patient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO {

    private Integer id_patient;
    private String last_name;
    private String first_name;
    private String email;
    private String phone;
    private String national_id;
    private Date birth_date;
    private Date created_at;
    private Date updated_at;
    private Boolean is_deleted;

    public PatientDTO(Patient patient) {
        this.id_patient = patient.getId_patient();
        this.last_name = patient.getLast_name();
        this.first_name = patient.getFirst_name();
        this.email = patient.getEmail();
        this.phone = patient.getPhone();
        this.national_id = patient.getNational_id();
        this.birth_date = patient.getBirth_date();
        this.created_at = patient.getCreated_at();
        this.updated_at = patient.getUpdated_at();
        this.is_deleted = patient.getIs_deleted();
    }
}