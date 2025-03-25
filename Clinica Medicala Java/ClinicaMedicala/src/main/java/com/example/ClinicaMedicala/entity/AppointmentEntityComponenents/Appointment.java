package com.example.ClinicaMedicala.entity.AppointmentEntityComponenents;

import com.example.ClinicaMedicala.dto.AppointmentDTOComponents.AppointmentDTO;
import com.example.ClinicaMedicala.entity.DoctorEntityComponents.DoctorMedicalServices;
import com.example.ClinicaMedicala.entity.PatientEntityComponents.Patient;
import com.example.ClinicaMedicala.enums.AppointmentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "appointments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_appointment;

    @ManyToOne
    @JoinColumn(name = "id_patient", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "id_doctor_medical_service", nullable = false)
    private DoctorMedicalServices doctorMedicalServices;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date appointment_date;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus appointment_status;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updated_at;

    @Column(nullable = false)
    private Boolean is_deleted = false;

    @OneToOne(mappedBy = "appointment", cascade = CascadeType.ALL)
    private MedicalLetter medicalLetter;

    public Appointment(AppointmentDTO appointmentDTO) {
        this.appointment_date = appointmentDTO.getAppointment_date();
        this.appointment_status = AppointmentStatus.valueOf(appointmentDTO.getAppointment_status());
        this.created_at = appointmentDTO.getCreated_at();
        this.updated_at = appointmentDTO.getUpdated_at();
        this.is_deleted = appointmentDTO.getIs_deleted();
    }
}
