package com.example.ClinicaMedicala.service.DoctorServiceComponents;

import com.example.ClinicaMedicala.dto.DoctorDTOComponents.DoctorScheduleDTO;
import com.example.ClinicaMedicala.entity.DoctorEntityComponents.Doctor;
import com.example.ClinicaMedicala.entity.DoctorEntityComponents.DoctorSchedule;
import com.example.ClinicaMedicala.enums.DayOfWeekSchedule;
import com.example.ClinicaMedicala.repository.DoctorRepositoryComponents.DoctorRepository;
import com.example.ClinicaMedicala.repository.DoctorRepositoryComponents.DoctorScheduleRepository;
import com.example.ClinicaMedicala.utils.CheckFields;
import com.example.ClinicaMedicala.utils.DTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.*;
import java.util.stream.Stream;

@Service
public class DoctorScheduleComponents {
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private DoctorScheduleRepository doctorScheduleRepository;

    public DoctorScheduleDTO addDoctorSchedule(DoctorScheduleDTO doctorScheduleDTO) {

        //verificari necesare
        StringBuilder errors = new StringBuilder();

        //verificare daca datele introduse sunt nule
        String emptyFieldsError = CheckFields.checkEmptyFields(
                DTOConverter.convertToMap(doctorScheduleDTO),
                Set.of("id_doctor_schedule", "id_doctor"));
        if (emptyFieldsError != null) {
            errors.append(emptyFieldsError)
                    .append(System.lineSeparator());
        }

        //verificare daca exista doctor cu id-ul primit
        Doctor doctor = doctorRepository.findDoctorById(doctorScheduleDTO.getId_doctor()).orElse(null);
        if(doctor == null) {
            errors.append("Doctorul cu id-ul: ").append(doctorScheduleDTO.getId_doctor()).append(" nu exista.")
                    .append(System.lineSeparator());
        }
        else{
            boolean existingSchedule = doctor.getDoctorSchedules().stream()
                    .filter(schedule -> !schedule.getIs_deleted())
                    .anyMatch(schedule -> schedule.getDay_of_week().name().equalsIgnoreCase(doctorScheduleDTO.getDay_of_week()));

            if(existingSchedule){
                errors.append("Doctorul cu acest id: ").append(doctorScheduleDTO.getId_doctor())
                        .append(" are deja program pentru ziua: ").append(doctorScheduleDTO.getDay_of_week())
                        .append(System.lineSeparator());
            }
        }

        //verificare daca timpul de start este inaintea celui de end
        if(doctorScheduleDTO.getStart_time().after(doctorScheduleDTO.getEnd_time())){
            errors.append("Ora de inceput nu poate fi mai mare decat ora de sfarsit")
                    .append(System.lineSeparator());
        }

        //verificare daca ziua saptamanii este corecta
        if (CheckFields.isValidEnumValue(
                Stream.of(DayOfWeekSchedule.values()).map(Enum::name).toList(),
                doctorScheduleDTO.getDay_of_week())) {
            errors.append("Aceasta zi a saptamanii: ")
                    .append(doctorScheduleDTO.getDay_of_week())
                    .append(" este incorecta")
                    .append(System.lineSeparator());
        }

        //afisarea erorilor
        if(!errors.isEmpty()) {
            throw new IllegalArgumentException(errors.toString().trim());
        }

        DoctorSchedule doctorSchedule = new DoctorSchedule(doctorScheduleDTO);
        doctorSchedule.setDoctor(doctor);
        doctorSchedule.setIs_deleted(false);

        DoctorSchedule savedDoctorSchedule = doctorScheduleRepository.save(doctorSchedule);
        return new DoctorScheduleDTO(savedDoctorSchedule);
    }

    public DoctorScheduleDTO updateDoctorSchedule(Integer id_doctor_schedule, Integer id_doctor,Map<String, Object> updates) {

        //verificari necesare
        StringBuilder errors = new StringBuilder();

        //verificare daca exista programul doctorului mentionat - daca nu exista, nu mai continuam verificarile
        DoctorSchedule doctorSchedule = doctorScheduleRepository.findDoctorScheduleById(id_doctor_schedule)
                .orElseThrow(() -> new IllegalArgumentException("Nu a fost gasit serviciul medical cu id-ul: " + id_doctor_schedule));

        if(!doctorSchedule.getDoctor().getId_doctor().equals(id_doctor)) {
            throw new IllegalArgumentException("Programul cu id-ul: " + id_doctor_schedule + " nu apartine doctorului cu id-ul: " + id_doctor);
        }

        //verificare daca nu se introduc date nule
        String emptyFieldsError = CheckFields.checkEmptyFields(
                DTOConverter.convertToMap(updates),
                Set.of("id_doctor_schedule", "id_doctor"));
        if (emptyFieldsError != null) {
            errors.append(emptyFieldsError)
                    .append(System.lineSeparator());
        }

        updates.forEach((field,value) ->{
            switch (field){
                case "day_of_week":
                    if (CheckFields.isValidEnumValue(
                            Stream.of(DayOfWeekSchedule.values()).map(Enum::name).toList(),
                            (String) value)) {
                        errors.append("Aceasta zi a saptamanii: ")
                                .append(value)
                                .append(" este incorecta")
                                .append(System.lineSeparator());
                    }
                    doctorSchedule.setDay_of_week(DayOfWeekSchedule.valueOf((String) value));
                    break;
                case "start_time":
                    Time newStartTime = Time.valueOf((String) value);
                    if(doctorSchedule.getEnd_time() != null && newStartTime.after(doctorSchedule.getEnd_time())) {
                        errors.append("Ora de inceput nu poate fi mai mare decat ora de sfarsit")
                                .append(System.lineSeparator());
                    }
                    doctorSchedule.setStart_time(newStartTime);
                    break;
                case "end_time":
                    Time newEndTime = Time.valueOf((String) value);
                    if(doctorSchedule.getStart_time() != null && newEndTime.before(doctorSchedule.getStart_time())) {
                        errors.append("Ora de inceput nu poate fi mai mica decat ora de inceput")
                                .append(System.lineSeparator());
                    }
                    break;
                case "id_doctor":
                case "id_doctor_schedule":
                case "is_deleted":
                    errors.append("Acest camp nu poate fi modificat: ").append(field)
                            .append(System.lineSeparator());
                    break;
                default:
                    errors.append("Acest camp nu exista: ").append(field)
                            .append(System.lineSeparator());
                    break;
            }
        });

        boolean existingSchedule = doctorSchedule.getDoctor().getDoctorSchedules().stream()
                .filter(schedule -> !schedule.getIs_deleted() && !schedule.getId_doctor_schedule().equals(id_doctor_schedule))
                .anyMatch(schedule -> schedule.getDay_of_week().name().equalsIgnoreCase(doctorSchedule.getDay_of_week().name()));

        if(existingSchedule){
            errors.append("Doctorul cu acest id: ").append(id_doctor)
                    .append(" are deja program pentru ziua: ").append(doctorSchedule.getDay_of_week())
                    .append(System.lineSeparator());
        }

        //afisarea erorilor
        if(!errors.isEmpty()) {
            throw new IllegalArgumentException(errors.toString().trim());
        }

        DoctorSchedule savedDoctorSchedule = doctorScheduleRepository.save(doctorSchedule);
        return new DoctorScheduleDTO(savedDoctorSchedule);
    }

    public void deleteDoctorSchedule(Integer id_doctor_schedule, Integer id_doctor) {
        DoctorSchedule doctorSchedule = doctorScheduleRepository.findDoctorScheduleById(id_doctor_schedule)
                .orElseThrow(() -> new IllegalArgumentException("Nu a fost gasit programul doctorului cu id-ul: " + id_doctor_schedule));

        if(!doctorSchedule.getDoctor().getId_doctor().equals(id_doctor)) {
            throw new IllegalArgumentException("Acest program nu apartine doctorului cu id-ul: "+ id_doctor);
        }

        if(doctorSchedule.getIs_deleted()){
            throw new IllegalArgumentException("Acest prograam al doctorului a fost deja sters");
        }

        doctorSchedule.setIs_deleted(true);
        doctorScheduleRepository.save(doctorSchedule);
    }

}