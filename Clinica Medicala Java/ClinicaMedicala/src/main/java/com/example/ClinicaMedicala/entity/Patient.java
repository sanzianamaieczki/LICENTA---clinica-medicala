package com.example.ClinicaMedicala.entity;

import com.example.ClinicaMedicala.dto.PatientDTO;
import com.example.ClinicaMedicala.entity.AppointmentEntityComponenents.Appointment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "patients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_patient;

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
    @Temporal(TemporalType.DATE)
    private Date birth_date;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updated_at;

    @Column(nullable = false)
    private Boolean is_deleted = false;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Appointment> appointment = new HashSet<>();

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
