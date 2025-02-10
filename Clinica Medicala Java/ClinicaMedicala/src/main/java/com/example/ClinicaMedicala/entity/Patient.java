package com.example.ClinicaMedicala.entity;

import com.example.ClinicaMedicala.dto.PatientDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "patients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_patient;

    @Column(nullable = false)
    private String last_name;

    @Column(nullable = false)
    private String first_name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String phone;

    @Column(unique = true, nullable = false)
    private String national_id;

    @Column(nullable = false)
    private Date birth_date;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at;

    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated_at;

    @Column(nullable = false)
    private Boolean is_deleted = false;

    public Patient(PatientDTO patientDTO){
        this.last_name = patientDTO.getLast_name();
        this.first_name = patientDTO.getFirst_name();
        this.email = patientDTO.getEmail();
        this.phone = patientDTO.getPhone();
        this.national_id = patientDTO.getNational_id();
        this.birth_date = patientDTO.getBirth_date();
        this.created_at = patientDTO.getCreated_at();
        this.updated_at = patientDTO.getUpdated_at();
        this.is_deleted = patientDTO.getIs_deleted();
    }
}
