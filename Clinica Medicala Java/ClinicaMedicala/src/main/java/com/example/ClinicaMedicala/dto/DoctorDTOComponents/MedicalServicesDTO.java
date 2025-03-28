package com.example.ClinicaMedicala.dto.DoctorDTOComponents;

import com.example.ClinicaMedicala.entity.DoctorEntityComponents.MedicalServices;
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
public class MedicalServicesDTO {

    private Integer id_medical_service;
    private String medical_service_name;
    private Date created_at;
    private Date updated_at;
    private Boolean is_deleted;

    private List<DoctorDetailsDTO> doctors;

    public MedicalServicesDTO(MedicalServices medicalServices) {
        this.id_medical_service = medicalServices.getId_medical_service();
        this.medical_service_name = medicalServices.getMedical_service_name();
        this.created_at = medicalServices.getCreated_at();
        this.updated_at = medicalServices.getUpdated_at();
        this.is_deleted = medicalServices.getIs_deleted();

        if(medicalServices.getDoctorMedicalServices() != null) {
            this.doctors = medicalServices.getDoctorMedicalServices().stream()
                    .filter(dms->!dms.getIs_deleted() && !dms.getDoctor().getIs_deleted())
                    .map(dms->new DoctorDetailsDTO(dms.getDoctor()))
                    .distinct()
                    .collect(Collectors.toList());
        }
        else {
            this.doctors = new ArrayList<>();
        }
    }
}