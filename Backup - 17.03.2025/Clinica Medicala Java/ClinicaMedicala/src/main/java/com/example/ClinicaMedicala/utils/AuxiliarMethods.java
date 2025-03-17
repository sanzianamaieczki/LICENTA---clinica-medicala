package com.example.ClinicaMedicala.utils;

import com.example.ClinicaMedicala.dto.AppointmentDTOComponents.AppointmentDTO;
import com.example.ClinicaMedicala.entity.DoctorEntityComponents.Doctor;
import com.example.ClinicaMedicala.entity.DoctorEntityComponents.DoctorSchedule;
import com.example.ClinicaMedicala.entity.DoctorEntityComponents.MedicalServices;
import com.example.ClinicaMedicala.repository.DoctorRepositoryComponents.DoctorScheduleRepository;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class AuxiliarMethods {
    // creare automata a datei de nastere in functie de CNP-ul persoanei
    public static Date extractBirthDateFromNationalId(String national_id) {
        if(national_id == null || national_id.length() < 13){
            throw new IllegalArgumentException("CNP invalid");
        }

        char s = national_id.charAt(0); //sex-ul persoanei (M/F)
        int year = Integer.parseInt(national_id.substring(1,3));
        int month = Integer.parseInt(national_id.substring(3,5));
        int day = Integer.parseInt(national_id.substring(5,7));

        if(s== '1' || s== '2'){
            year = 1900 + year;
        }
        else if(s== '5' || s== '6'){
            year = 2000 + year;
        }
        else {
            throw new IllegalArgumentException("CNP invalid. Prefix necunoscut");
        }

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.set(year,month-1,day,0,0,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTime();
    }

//    public static void checkDoctorSchedule(AppointmentDTO appointmentDTO,
//           MedicalServices medicalServices,
//           DoctorScheduleRepository doctorScheduleRepository,
//           StringBuilder errors) {
//        Doctor doctor = medicalServices.getDoctor();
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(appointmentDTO.getAppointment_date());
//        String dayOfWeek = String.valueOf(calendar.get(Calendar.DAY_OF_WEEK));
//
//        List<DoctorSchedule> doctorSchedules = doctorScheduleRepository.findDoctorScheduleByDoctorId(doctor.getId_doctor());
//        DoctorSchedule doctorSchedule = doctorSchedules.stream()
//                .filter(ds-> ds.getDay_of_week().name().equalsIgnoreCase(dayOfWeek) && !ds.getIs_deleted()).
//                findFirst().orElse(null);
//
//        if(doctorSchedule == null) {
//            errors.append("Doctorul nu are program pentru ziua: ")
//                    .append(dayOfWeek)
//                    .append(System.lineSeparator());
//        }
//        else{
//            Time appoimentStartTime = new Time(appointmentDTO.getAppointment_date().getTime());
//            Calendar appoimentEndTimeCalendar = Calendar.getInstance();
//            appoimentEndTimeCalendar.setTime(appointmentDTO.getAppointment_date());
//            appoimentEndTimeCalendar.add(Calendar.MINUTE, medicalServices.getDuration());
//            Time appoimentEndTime = new Time(appoimentEndTimeCalendar.getTime().getTime());
//
//            if(appoimentStartTime.before(doctorSchedule.getStart_time()) ||
//                    appoimentEndTime.after(doctorSchedule.getEnd_time())) {
//                errors.append("Doctorul nu este disponibil intre orele: ")
//                        .append(doctorSchedule.getStart_time())
//                        .append(" - ")
//                        .append(doctorSchedule.getEnd_time())
//                        .append(" in ziua: ")
//                        .append(dayOfWeek)
//                        .append(System.lineSeparator());
//            }
//        }
//    }
}
