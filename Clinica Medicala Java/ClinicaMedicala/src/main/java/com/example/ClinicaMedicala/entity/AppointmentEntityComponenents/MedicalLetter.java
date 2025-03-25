package com.example.ClinicaMedicala.entity.AppointmentEntityComponenents;

import com.example.ClinicaMedicala.dto.AppointmentDTOComponents.AppointmentDTO;
import com.example.ClinicaMedicala.dto.AppointmentDTOComponents.MedicalLetterDTO;
import com.example.ClinicaMedicala.enums.AppointmentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "medical_letters")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicalLetter {

    @Id
    @GeneratedValue
    private Integer id_medical_letter;

    @OneToOne
    @JoinColumn(name = "id_appointment", nullable = false,unique = true)
    private Appointment appointment;

    @Column(nullable = false)
    private String diagnosis;

    @Column(nullable = false)
    private String symptoms;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updated_at;

    @Column(nullable = false)
    private Boolean is_deleted = false;

    public MedicalLetter(MedicalLetterDTO medicalLetterDTO) {
        this.diagnosis = medicalLetterDTO.getDiagnosis();
        this.symptoms = medicalLetterDTO.getSymptoms();
        this.content = medicalLetterDTO.getContent();
        this.created_at = medicalLetterDTO.getCreated_at();
        this.updated_at = medicalLetterDTO.getUpdated_at();
        this.is_deleted = medicalLetterDTO.getIs_deleted();
    }
}
