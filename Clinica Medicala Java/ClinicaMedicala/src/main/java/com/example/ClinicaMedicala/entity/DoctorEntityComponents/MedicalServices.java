package com.example.ClinicaMedicala.entity.DoctorEntityComponents;

import com.example.ClinicaMedicala.dto.DoctorDTOComponents.MedicalServicesDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "medical_services")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicalServices {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_medical_service;

    @Column(nullable = false)
    private String medical_service_name;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updated_at;

    @Column(nullable = false)
    private Boolean is_deleted = false;

    @OneToMany(mappedBy = "medicalService", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<DoctorMedicalServices> doctorMedicalServices = new HashSet<>();

    public MedicalServices(MedicalServicesDTO medicalServicesDTO){
        this.medical_service_name = medicalServicesDTO.getMedical_service_name();
        this.created_at = medicalServicesDTO.getCreated_at();
        this.updated_at = medicalServicesDTO.getUpdated_at();
        this.is_deleted = medicalServicesDTO.getIs_deleted();
    }
}
