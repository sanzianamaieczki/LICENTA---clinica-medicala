package com.example.ClinicaMedicala.entity.DoctorEntityComponents;

import com.example.ClinicaMedicala.dto.DoctorDTOComponents.DoctorScheduleDTO;
import com.example.ClinicaMedicala.enums.DayOfWeek;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;

@Entity
@Table(name = "doctor_schedule")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_doctor_schedule;

    @ManyToOne
    @JoinColumn(name = "id_doctor", nullable = false)
    private Doctor doctor;

    @Enumerated(EnumType.STRING)
    private DayOfWeek day_of_week;

    @Column(nullable = false)
    @Temporal(TemporalType.TIME)
    private Time start_time;

    @Column(nullable = false)
    @Temporal(TemporalType.TIME)
    private Time end_time;

    @Column(nullable = false)
    private Boolean is_deleted;

    public DoctorSchedule(DoctorScheduleDTO doctorScheduleDTO){
        this.day_of_week = DayOfWeek.valueOf(doctorScheduleDTO.getDay_of_week());
        this.start_time = doctorScheduleDTO.getStart_time();
        this.end_time = doctorScheduleDTO.getEnd_time();
        this.is_deleted = doctorScheduleDTO.getIs_deleted();
    }
}
