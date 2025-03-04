package com.example.ClinicaMedicala.repository;

import com.example.ClinicaMedicala.entity.DoctorSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Time;
import java.util.List;
import java.util.Optional;

public interface DoctorScheduleRepository extends JpaRepository<DoctorSchedule, Integer> {

    @Query("SELECT ds FROM DoctorSchedule ds " +
            "WHERE (:is_deleted IS NULL OR ds.is_deleted = :is_deleted) " +
            "AND (:day_of_week IS NULL OR LOWER(ds.day_of_week) LIKE LOWER(CONCAT('%', :day_of_week, '%'))) " +
            "AND ((:start_time IS NULL AND :end_time IS NULL) " +
            "     OR (:start_time IS NOT NULL AND :end_time IS NULL AND ds.start_time >= :start_time) " +
            "     OR (:start_time IS NULL AND :end_time IS NOT NULL AND ds.end_time <= :end_time) " +
            "     OR (:start_time IS NOT NULL AND :end_time IS NOT NULL AND ds.start_time >= :start_time AND ds.end_time <= :end_time))"
    )
    List<DoctorSchedule> findDoctorScheduleByFilters(
            @Param("is_deleted") Boolean is_deleted,
            @Param("day_of_week") String day_of_week,
            @Param("start_time") Time start_time,
            @Param("end_time") Time end_time
    );

    @Query("SELECT ds FROM DoctorSchedule ds WHERE ds.id_doctor_schedule = :id_doctor_schedule")
    Optional<DoctorSchedule> findDoctorScheduleById(int id_doctor_schedule);

    @Query("SELECT ds FROM DoctorSchedule ds WHERE ds.doctor.id_doctor = :id_doctor")
    List<DoctorSchedule> findDoctorScheduleByDoctorId(int id_doctor);
}
