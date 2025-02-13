package com.example.ClinicaMedicala.dto;

import com.example.ClinicaMedicala.entity.Specialization;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpecializationDTO {

    private Integer id_specialization;
    private String specialization_name;
    private Date created_at;
    private Date updated_at;
    private Boolean is_deleted;

    public SpecializationDTO(Specialization specialization) {
        this.id_specialization = specialization.getId_specialization();
        this.specialization_name = specialization.getSpecialization_name();
        this.created_at = specialization.getCreated_at();
        this.updated_at = specialization.getUpdated_at();
        this.is_deleted = specialization.getIs_deleted();
    }
}
