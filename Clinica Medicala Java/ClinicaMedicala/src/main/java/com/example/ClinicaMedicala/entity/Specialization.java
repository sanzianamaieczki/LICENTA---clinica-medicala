package com.example.ClinicaMedicala.entity;

import com.example.ClinicaMedicala.dto.SpecializationDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "specializations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Specialization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_specialization;

    @Column(nullable = false, unique = true)
    private String specialization_name;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at;

    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated_at;

    @Column(nullable = false)
    private Boolean is_deleted = false;

    public Specialization(SpecializationDTO specializationDTO) {
       this.specialization_name = specializationDTO.getSpecialization_name();
       this.created_at = specializationDTO.getCreated_at();
       this.updated_at = specializationDTO.getUpdated_at();
       this.is_deleted = specializationDTO.getIs_deleted();
    }
}
