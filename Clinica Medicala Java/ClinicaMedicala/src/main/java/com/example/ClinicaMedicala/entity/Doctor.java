package com.example.ClinicaMedicala.entity;

import com.example.ClinicaMedicala.dto.DoctorDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "doctors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_doctor;

    @Column(nullable = false)
    private String last_name;

    @Column(nullable = false)
    private String first_name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String phone;

    @ManyToOne
    @JoinColumn(name = "id_specialization", nullable = false)
    private Specialization specialization;

    @ManyToOne
    @JoinColumn(name = "id_clinic", nullable = false)
    private Clinic clinic;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at;

    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated_at;

    @Column(nullable = false)
    private Boolean is_deleted = false;

    public Doctor(DoctorDTO doctorDTO){
        this.last_name = doctorDTO.getLast_name();
        this.first_name = doctorDTO.getFirst_name();
        this.email = doctorDTO.getEmail();
        this.phone = doctorDTO.getPhone();
        this.created_at = doctorDTO.getCreated_at();
        this.updated_at = doctorDTO.getUpdated_at();
        this.is_deleted = doctorDTO.getIs_deleted();
    }
}
