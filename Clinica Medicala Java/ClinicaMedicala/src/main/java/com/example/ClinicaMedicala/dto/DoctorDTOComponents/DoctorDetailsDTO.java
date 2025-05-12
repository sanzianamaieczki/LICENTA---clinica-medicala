package com.example.ClinicaMedicala.dto.DoctorDTOComponents;

import com.example.ClinicaMedicala.entity.DoctorEntityComponents.Doctor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDetailsDTO {
    private Integer id_doctor;
    private String last_name;
    private String first_name;
    private String email;
    private String phone;
    private Integer id_specialization;

    public DoctorDetailsDTO(Doctor doctor) {
        this.id_doctor = doctor.getId_doctor();
        this.last_name = doctor.getLast_name();
        this.first_name = doctor.getFirst_name();
        this.email = doctor.getEmail();
        this.phone = doctor.getPhone();
        this.id_specialization = doctor.getSpecialization().getId_specialization();
    }
}
