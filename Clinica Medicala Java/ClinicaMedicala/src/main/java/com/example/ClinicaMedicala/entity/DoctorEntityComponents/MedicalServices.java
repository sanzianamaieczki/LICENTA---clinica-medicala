package com.example.ClinicaMedicala.entity.DoctorEntityComponents;

import com.example.ClinicaMedicala.dto.AppointmentDTOComponents.AppointmentDTO;
import com.example.ClinicaMedicala.dto.DoctorDTOComponents.MedicalServicesDTO;
import com.example.ClinicaMedicala.entity.AppointmentEntityComponenents.Appointment;
import com.example.ClinicaMedicala.enums.MedicalServicesType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
//
//    @ManyToOne
//    @JoinColumn(name = "id_doctor", nullable = false)
//    private Doctor doctor;

    @Column(nullable = false)
    private String medical_service_name;

//    @Column(nullable = false)
//    private Double price;

//
//    @Column(nullable = false)
//    private Integer duration;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updated_at;

    @Column(nullable = false)
    private Boolean is_deleted = false;

    @OneToMany(mappedBy = "medicalServices", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<DoctorMedicalServices> doctorMedicalServices = new HashSet<>();

    public MedicalServices(MedicalServicesDTO medicalServicesDTO){
        this.medical_service_name = medicalServicesDTO.getMedical_service_name();
        this.created_at = medicalServicesDTO.getCreated_at();
        this.updated_at = medicalServicesDTO.getUpdated_at();
        this.is_deleted = medicalServicesDTO.getIs_deleted();
    }
}
