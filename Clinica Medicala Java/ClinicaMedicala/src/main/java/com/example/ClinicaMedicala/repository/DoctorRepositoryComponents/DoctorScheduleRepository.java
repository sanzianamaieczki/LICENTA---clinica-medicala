package com.example.ClinicaMedicala.repository.DoctorRepositoryComponents;

import com.example.ClinicaMedicala.entity.DoctorEntityComponents.DoctorSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DoctorScheduleRepository extends JpaRepository<DoctorSchedule, Integer> {

    @Query("SELECT ds FROM DoctorSchedule ds WHERE ds.id_doctor_schedule = :id_doctor_schedule")
    Optional<DoctorSchedule> findDoctorScheduleById(int id_doctor_schedule);
}
