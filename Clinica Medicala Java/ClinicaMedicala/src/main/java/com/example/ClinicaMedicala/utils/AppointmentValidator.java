package com.example.ClinicaMedicala.utils;

import com.example.ClinicaMedicala.entity.DoctorEntityComponents.Doctor;
import com.example.ClinicaMedicala.entity.PatientEntityComponents.Patient;
import com.example.ClinicaMedicala.enums.AppointmentStatus;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

public class AppointmentValidator {
    public static String validateAppointment(Doctor doctor, Patient patient, Date appointment_date, Integer currentIdAppointment) {
        StringBuilder errors = new StringBuilder();

        // o programare dureaza 30 min
        LocalDateTime newStart = LocalDateTime.ofInstant(appointment_date.toInstant(), ZoneId.systemDefault());
        LocalDateTime newEnd = newStart.plusMinutes(30);
        DayOfWeek appointmentDay = newStart.getDayOfWeek();
        LocalTime newStartTime = newStart.toLocalTime();
        LocalTime newEndTime = newEnd.toLocalTime();

        boolean validSchedule = doctor.getDoctorSchedules().stream()
                .filter(doctorSchedule -> !doctorSchedule.getIs_deleted()
                &&doctorSchedule.getDay_of_week().name().equalsIgnoreCase(appointmentDay.name()))
                .anyMatch(doctorSchedule -> {
                    LocalTime scheduleStart = doctorSchedule.getStart_time().toLocalTime();
                    LocalTime scheduleEnd = doctorSchedule.getEnd_time().toLocalTime();
                    LocalTime allowedMax = scheduleStart.plusMinutes(30);

                    return !newStartTime.isBefore(scheduleStart)
                            && !newEndTime.isAfter(scheduleEnd)
                            && !newStartTime.isAfter(allowedMax);
                });
        if(!validSchedule){
            errors.append("Doctorul nu este disponibil la ora selectata")
                    .append(System.lineSeparator());
        }

        if(doctor.getDoctorMedicalServices().stream()
                .flatMap(medicalService -> medicalService.getAppointment().stream())
                .filter(appointment -> !appointment.getIs_deleted() && appointment.getAppointment_status() != AppointmentStatus.canceled
                &&(currentIdAppointment == null || !appointment.getId_appointment().equals(currentIdAppointment)))
                .anyMatch(appointment -> {
                    LocalDateTime appointmentStart = LocalDateTime.ofInstant(appointment.getAppointment_date().toInstant(), ZoneId.systemDefault());
                    LocalDateTime appointmentEnd = appointmentStart.plusMinutes(30);

                    return newStart.isBefore(appointmentEnd) && appointmentStart.isBefore(newEnd);
                }))
        {
            errors.append("Este o alta programare la aceasta ora pentru acest serviciu medical.")
                    .append(System.lineSeparator());
        }

        if(patient.getAppointment().stream()
                .filter(appointment -> !appointment.getIs_deleted() && appointment.getAppointment_status() != AppointmentStatus.canceled
                &&(currentIdAppointment == null || !appointment.getId_appointment().equals(currentIdAppointment)))
                .anyMatch(appointment -> {
                    LocalDateTime appointmentStart = LocalDateTime.ofInstant(appointment.getAppointment_date().toInstant(), ZoneId.systemDefault());
                    LocalDateTime appointmentEnd = appointmentStart.plusMinutes(30);

                    return newStart.isBefore(appointmentEnd) && appointmentStart.isBefore(newEnd);
                })
        ){
            errors.append("Acest pacient mai are o programare la aceasta ora.")
                    .append(System.lineSeparator());
        }

        return errors.toString().trim();
    }
}
