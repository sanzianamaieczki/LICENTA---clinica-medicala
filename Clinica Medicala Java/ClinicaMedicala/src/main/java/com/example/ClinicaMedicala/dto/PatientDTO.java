package com.example.ClinicaMedicala.dto;

import com.example.ClinicaMedicala.dto.AppointmentDTOComponents.AppointmentDTO;
import com.example.ClinicaMedicala.entity.Patient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO {

    private Integer id_patient;
    private String last_name;
    private String first_name;
    private String email;
    private String phone;
    private String national_id;
    private Date birth_date;
    private Date created_at;
    private Date updated_at;
    private Boolean is_deleted;

    private List<AppointmentDTO> appointments;

    public PatientDTO(Patient patient) {
        this.id_patient = patient.getId_patient();
        this.last_name = patient.getLast_name();
        this.first_name = patient.getFirst_name();
        this.email = patient.getEmail();
        this.phone = patient.getPhone();
        this.national_id = patient.getNational_id();
        this.birth_date = patient.getBirth_date();
        this.created_at = patient.getCreated_at();
        this.updated_at = patient.getUpdated_at();
        this.is_deleted = patient.getIs_deleted();

        if(patient.getAppointment() != null) {
            this.appointments = patient.getAppointment().stream()
                    .map(AppointmentDTO::new)
                    .collect(Collectors.toList());
        } else {
            this.appointments = new ArrayList<>();
        }
    }
}