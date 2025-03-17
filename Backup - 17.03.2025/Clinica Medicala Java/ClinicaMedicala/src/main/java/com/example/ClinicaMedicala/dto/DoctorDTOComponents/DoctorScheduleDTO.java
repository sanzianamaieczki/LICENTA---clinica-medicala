package com.example.ClinicaMedicala.dto.DoctorDTOComponents;

import com.example.ClinicaMedicala.entity.DoctorEntityComponents.DoctorSchedule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorScheduleDTO {

    private Integer id_doctor_schedule;
    private Integer id_doctor;
    private String day_of_week;
    private Time start_time;
    private Time end_time;
    private Boolean is_deleted;

    public DoctorScheduleDTO(DoctorSchedule doctorSchedule) {
        this.id_doctor_schedule = doctorSchedule.getId_doctor_schedule();
        this.id_doctor = doctorSchedule.getDoctor().getId_doctor();
        this.day_of_week = doctorSchedule.getDay_of_week().name();
        this.start_time = doctorSchedule.getStart_time();
        this.end_time = doctorSchedule.getEnd_time();
        this.is_deleted = doctorSchedule.getIs_deleted();
    }
}