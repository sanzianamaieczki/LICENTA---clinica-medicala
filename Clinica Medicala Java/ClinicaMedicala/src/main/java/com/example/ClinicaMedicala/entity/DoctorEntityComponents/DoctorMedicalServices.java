package com.example.ClinicaMedicala.entity.DoctorEntityComponents;

import com.example.ClinicaMedicala.dto.DoctorDTOComponents.DoctorMedicalServicesDTO;
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
@Table(name = "doctor_medical_services")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorMedicalServices {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_doctor_medical_service;

    @ManyToOne
    @JoinColumn(name = "id_doctor", nullable = false)
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "id_medical_service", nullable = false)
    private MedicalServices medicalService;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updated_at;

    @Column(nullable = false)
    private Boolean is_deleted = false;

    @OneToMany(mappedBy = "doctorMedicalServices", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Appointment> appointment = new HashSet<>();

    public DoctorMedicalServices(DoctorMedicalServicesDTO doctorMedicalServicesDTO){
        this.price = doctorMedicalServicesDTO.getPrice();
        this.created_at = doctorMedicalServicesDTO.getCreated_at();
        this.updated_at = doctorMedicalServicesDTO.getUpdated_at();
        this.is_deleted = doctorMedicalServicesDTO.getIs_deleted();
    }
}
