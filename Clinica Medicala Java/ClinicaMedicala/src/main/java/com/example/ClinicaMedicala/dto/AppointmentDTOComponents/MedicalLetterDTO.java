package com.example.ClinicaMedicala.dto.AppointmentDTOComponents;

import com.example.ClinicaMedicala.entity.AppointmentEntityComponenents.MedicalLetter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicalLetterDTO {
    private Integer id_medical_letter;
    private Integer id_appointment;
    private String diagnosis;
    private String symptoms;
    private String content;
    private Date created_at;
    private Date updated_at;
    private Boolean is_deleted;

    public MedicalLetterDTO(MedicalLetter medicalLetter) {
        this.id_medical_letter = medicalLetter.getId_medical_letter();
        this.id_appointment = medicalLetter.getAppointment().getId_appointment();
        this.diagnosis = medicalLetter.getDiagnosis();
        this.symptoms = medicalLetter.getSymptoms();
        this.content = medicalLetter.getContent();
        this.created_at = medicalLetter.getCreated_at();
        this.updated_at = medicalLetter.getUpdated_at();
        this.is_deleted = medicalLetter.getIs_deleted();
    }
}