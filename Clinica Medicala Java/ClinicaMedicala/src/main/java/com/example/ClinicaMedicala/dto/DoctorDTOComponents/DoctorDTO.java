package com.example.ClinicaMedicala.dto.DoctorDTOComponents;

import com.example.ClinicaMedicala.entity.DoctorEntityComponents.Doctor;
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
public class DoctorDTO extends DoctorDetailsDTO{

    private Integer id_specialization;
    private Integer id_clinic;
    private Date created_at;
    private Date updated_at;
    private Boolean is_deleted;

    private List<DoctorMedicalServicesDTO> medicalServices;
    private List<DoctorScheduleDTO> doctorSchedules;

    public DoctorDTO(Doctor doctor) {

        super(doctor);
        this.id_specialization = doctor.getSpecialization().getId_specialization();
        this.id_clinic = doctor.getClinic().getId_clinic();
        this.created_at = doctor.getCreated_at();
        this.updated_at = doctor.getUpdated_at();
        this.is_deleted = doctor.getIs_deleted();

        if (doctor.getMedicalServices() != null) {
            this.medicalServices = doctor.getDoctorMedicalServices().stream()
                    // pentru a nu afisa serviciile medicale sterse
                    .filter(doctorMedicalServices -> !doctorMedicalServices.getIs_deleted())
                    .map(DoctorMedicalServicesDTO::new)
                    .collect(Collectors.toList());
        } else {
            this.medicalServices = new ArrayList<>();
        }

        if(doctor.getDoctorSchedules() != null) {
            this.doctorSchedules = doctor.getDoctorSchedules().stream()
                    .map(DoctorScheduleDTO::new)
                    .collect(Collectors.toList());
        } else {
            this.doctorSchedules = new ArrayList<>();
        }

    }
}