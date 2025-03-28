package com.example.ClinicaMedicala.dto.ClinicDTOComponents;

import com.example.ClinicaMedicala.dto.DoctorDTOComponents.DoctorDetailsDTO;
import com.example.ClinicaMedicala.entity.ClinicEntityComponents.Clinic;
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
public class ClinicDTO {

    private Integer id_clinic;
    private String clinic_name;
    private String clinic_phone;
    private String clinic_address;
    private Date created_at;
    private Date updated_at;
    private Boolean is_deleted;

    private List<DoctorDetailsDTO> doctors;

    public ClinicDTO(Clinic clinic) {
        this.id_clinic = clinic.getId_clinic();
        this.clinic_name = clinic.getClinic_name();
        this.clinic_phone = clinic.getClinic_phone();
        this.clinic_address = clinic.getClinic_address();
        this.created_at = clinic.getCreated_at();
        this.updated_at = clinic.getUpdated_at();
        this.is_deleted = clinic.getIs_deleted();

        if(clinic.getDoctors() != null) {
            this.doctors = clinic.getDoctors().stream()
                    .filter(doctor -> !doctor.getIs_deleted())
                    .map(DoctorDetailsDTO::new)
                    .collect(Collectors.toList());
        }
        else {
            this.doctors = new ArrayList<>();
        }
    }
}
