package com.example.ClinicaMedicala.service.AppointmentServiceComponents;

import com.example.ClinicaMedicala.dto.AppointmentDTOComponents.AppointmentDTO;
import com.example.ClinicaMedicala.entity.AppointmentEntityComponenents.Appointment;
import com.example.ClinicaMedicala.entity.DoctorEntityComponents.Doctor;
import com.example.ClinicaMedicala.entity.DoctorEntityComponents.DoctorMedicalServices;
import com.example.ClinicaMedicala.entity.Patient;
import com.example.ClinicaMedicala.enums.AppointmentStatus;
import com.example.ClinicaMedicala.repository.AppointmentRepositoryComponents.AppointmentRepository;
import com.example.ClinicaMedicala.repository.DoctorRepositoryComponents.DoctorRepository;
import com.example.ClinicaMedicala.repository.PatientRepository;
import com.example.ClinicaMedicala.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AppointmentComponent {
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private PatientRepository patientRepository;

    public List<AppointmentDTO> getAppointmentsByFilters(
            Boolean is_deleted,
            String appointment_status,
            Date appointment_date
    ){
        return appointmentRepository.findAppointmentsByFilters(is_deleted, appointment_status, appointment_date).stream()
                .map(AppointmentDTO::new)
                .collect(Collectors.toList());
    }
    public Optional<AppointmentDTO> getAppointmentById(int id_appointment) {
        return appointmentRepository.findAppointmentById(id_appointment)
                .map(AppointmentDTO::new);
    }

    public AppointmentDTO addAppointment(AppointmentDTO appointmentDTO) {

        //verificari necesare
        StringBuilder errors = new StringBuilder();

        //verificare daca datele introduse sunt nule
        String emptyFieldsError = CheckFields.checkEmptyFields(
                DTOConverter.convertToMap(appointmentDTO),
                Set.of("id_appointment", "appointment_status"));
        if (emptyFieldsError != null) {
            errors.append(emptyFieldsError)
                    .append(System.lineSeparator());
        }

        //lista programarilor existente
        List<AppointmentDTO> existingAppointments = getAppointmentsByFilters(null, null,null);

        Doctor doctor = doctorRepository
                .findDoctorByDoctorMedicalServiceId((appointmentDTO.getId_doctor_medical_service()))
                .orElseThrow(()-> new IllegalArgumentException("Nu a fost gasit doctorul pentru acest serviciu medical"));

        DoctorMedicalServices doctorMedicalServices = doctor.getDoctorMedicalServices().stream()
                .filter(doctorMedicalService -> doctorMedicalService.getId_doctor_medical_service().equals(appointmentDTO.getId_doctor_medical_service()))
                .findFirst()
                .orElseThrow(()-> new IllegalArgumentException("Nu a fost gasit serviciul medical pentru doctor"));

        Patient patient = patientRepository.findPatientById(appointmentDTO.getId_patient())
                .orElseThrow(()-> new IllegalArgumentException("Nu exista acest pacient"));

        Date appointment_date = appointmentDTO.getAppointment_date();

        String appointmentValidation = AppointmentValidator.validateAppointment(doctor,patient,appointment_date, null);
        if(!appointmentValidation.isEmpty()){
            errors.append(appointmentValidation);
        }

        //afisarea erorilor
        if(!errors.isEmpty()) {
            throw new IllegalArgumentException(errors.toString().trim());
        }

        appointmentDTO.setAppointment_status(String.valueOf(AppointmentStatus.created));

        Appointment appointment = new Appointment(appointmentDTO);
        appointment.setPatient(patient);
        appointment.setAppointment_date(appointment_date);
        appointment.setDoctorMedicalServices(doctorMedicalServices);
        appointment.setCreated_at(new Date());
        appointment.setUpdated_at(null);
        appointment.setIs_deleted(false);

        Appointment savedAppointment = appointmentRepository.save(appointment);
        return new AppointmentDTO(savedAppointment);
    }

    public AppointmentDTO updateAppointment(Integer id_appointment, Map<String, Object> updates) {
        //verificari necesare
        StringBuilder errors = new StringBuilder();

        Appointment appointment = appointmentRepository.findAppointmentById(id_appointment)
                .orElseThrow(() -> new IllegalArgumentException("Nu a fost gasita programarea cu id-ul: " + id_appointment));

        //verificare daca datele introduse sunt nule
        String emptyFieldsError = CheckFields.checkEmptyFields(
                DTOConverter.convertToMap(updates),
                Set.of("id_appointment", "appointment_status"));
        if (emptyFieldsError != null) {
            errors.append(emptyFieldsError)
                    .append(System.lineSeparator());
        }

        updates.forEach((field, value) -> {
            switch (field) {
                case "id_patient":
                    try {
                        Integer id_patient = Integer.parseInt(value.toString());
                        Patient patient = patientRepository.findPatientById(id_patient).orElse(null);
                        if (patient == null) {
                            errors.append(" Nu a fost gasit pacientul cu id-ul: ").append(id_patient)
                                    .append(System.lineSeparator());
                        }
                        appointment.setPatient(patient);
                    } catch (NumberFormatException e) {
                        errors.append("ID-ul pacientului trebuie sa fie un numar valid.")
                                .append(System.lineSeparator());
                    }
                    break;
                case "id_medical_service":
                    try {
                        Integer id_medical_service = Integer.parseInt(value.toString());
                        Doctor doctor = doctorRepository.findDoctorByDoctorMedicalServiceId(id_medical_service).orElse(null);
                        if (doctor != null) {
                            DoctorMedicalServices medicalServices = doctor.getDoctorMedicalServices().stream()
                                    .filter(medicalService -> medicalService.getId_doctor_medical_service().equals(id_medical_service))
                                    .findFirst()
                                    .orElse(null);
                            if (medicalServices == null) {
                                errors.append("Nu am gasit serviciul medical cu id-ul: ").append(id_medical_service)
                                        .append(System.lineSeparator());
                            }
                            appointment.setDoctorMedicalServices(medicalServices);
                        }

                    } catch (NumberFormatException e) {
                        errors.append("ID-ul serviciului medical trebuie sa fie un numar valid.")
                                .append(System.lineSeparator());
                    }
                    break;
                case "appointment_date":
                    try{
                        Date newDate;
                        if(value instanceof Date){
                            newDate = (Date) value;
                        }
                        else{
                            OffsetDateTime dateTime = OffsetDateTime.parse((String) value);
                            newDate =Date.from(dateTime.toInstant());
                        }
                        appointment.setAppointment_date(newDate);
                    }catch (Exception e){
                        errors.append("Data programarii este invalida")
                                .append(System.lineSeparator());
                    }
                    break;
                case "id_appointment":
                case "appointment_status":
                case "created_at":
                case "updated_at":
                case "is_deleted":
                    errors.append("Acest camp nu poate fi modificat: ").append(field);
                    break;
                default:
                    errors.append("Acest camp nu exista: ").append(field);
                    break;
            }
            //afisarea erorilor
            if (!errors.isEmpty()) {
                throw new IllegalArgumentException(errors.toString().trim());
            }
        });

        boolean mustValidate = updates.containsKey("appointment_date")|| updates.containsKey("id_patient")
                || updates.containsKey("id_medical_service");

        Doctor doctor = appointment.getDoctorMedicalServices().getDoctor();
        Patient patient = appointment.getPatient();
        Date appointment_date = appointment.getAppointment_date();

        if(mustValidate) {
            if (doctor == null) {
                errors.append("Doctorul nu este valid.").append(System.lineSeparator());
            } else if (patient == null) {
                errors.append("Pacientul nu este valid.").append(System.lineSeparator());
            } else if (appointment_date == null) {
                errors.append("Data programarii nu este valida.").append(System.lineSeparator());
            } else {
                String appointmentValidation = AppointmentValidator.validateAppointment(doctor, patient, appointment_date, appointment.getId_appointment());
                if (!appointmentValidation.isEmpty()) {
                    errors.append(appointmentValidation);
                }
            }
        }

        //afisarea erorilor
        if(!errors.isEmpty()) {
            throw new IllegalArgumentException(errors.toString().trim());
        }

        appointment.setUpdated_at(new Date());
        Appointment savedAppointment = appointmentRepository.save(appointment);
        return new AppointmentDTO(savedAppointment);
    }

    public void deleteAppointment(Integer id_appointment) {
        Appointment appointment = appointmentRepository.findAppointmentById(id_appointment)
                .orElseThrow(() -> new IllegalArgumentException("Nu a fost gasita programarea cu id-ul: " + id_appointment));

        if(appointment.getIs_deleted()){
            throw  new IllegalArgumentException("Aceasta programare a fost deja stearsa: " + id_appointment);
        }

        appointment.setAppointment_status(AppointmentStatus.canceled);
        appointment.setUpdated_at(new Date());
        appointmentRepository.save(appointment);
    }
}
