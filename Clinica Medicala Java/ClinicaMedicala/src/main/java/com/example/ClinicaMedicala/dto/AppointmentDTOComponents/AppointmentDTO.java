package com.example.ClinicaMedicala.dto.AppointmentDTOComponents;

import com.example.ClinicaMedicala.entity.AppointmentEntityComponenents.Appointment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDTO{
    private Integer id_appointment;
    private Integer id_patient;
    private Integer id_doctor_medical_service;
    private Date appointment_date;
    private String appointment_status;
    private Date created_at;
    private Date updated_at;
    private Boolean is_deleted;

    private MedicalLetterDTO medicalLetter;

    public AppointmentDTO(Appointment appointment) {
        this.id_appointment = appointment.getId_appointment();
        this.id_patient = appointment.getPatient().getId_patient();
        this.id_doctor_medical_service = appointment.getDoctorMedicalServices().getId_doctor_medical_service();
        this.appointment_date = appointment.getAppointment_date();
        this.appointment_status = appointment.getAppointment_status().name();
        this.created_at = appointment.getCreated_at();
        this.updated_at = appointment.getUpdated_at();
        this.is_deleted = appointment.getIs_deleted();

        if(appointment.getMedicalLetter()!=null){
            this.medicalLetter = new MedicalLetterDTO(appointment.getMedicalLetter());
        }
    }
}