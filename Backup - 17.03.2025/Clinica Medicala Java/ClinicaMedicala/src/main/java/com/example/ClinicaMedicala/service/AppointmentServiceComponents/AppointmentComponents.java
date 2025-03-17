package com.example.ClinicaMedicala.service.AppointmentServiceComponents;

import com.example.ClinicaMedicala.dto.AppointmentDTOComponents.AppointmentDTO;
import com.example.ClinicaMedicala.entity.AppointmentEntityComponents.Appointment;
import com.example.ClinicaMedicala.entity.DoctorEntityComponents.MedicalServices;
import com.example.ClinicaMedicala.entity.PatientEntityComponents.Patient;
import com.example.ClinicaMedicala.enums.AppointmentEnumComponent.AppointmentStatus;
import com.example.ClinicaMedicala.repository.AppointmentRepositoryComponents.AppointmentRepository;
import com.example.ClinicaMedicala.repository.DoctorRepositoryComponents.DoctorScheduleRepository;
import com.example.ClinicaMedicala.repository.DoctorRepositoryComponents.MedicalServicesRepository;
import com.example.ClinicaMedicala.repository.PatientRepositoryComponents.PatientRepository;
import com.example.ClinicaMedicala.utils.AuxiliarMethods;
import com.example.ClinicaMedicala.utils.CheckFields;
import com.example.ClinicaMedicala.utils.DTOConverter;
import com.example.ClinicaMedicala.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AppointmentComponents {
    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    MedicalServicesRepository medicalServicesRepository;
    @Autowired
    DoctorScheduleRepository doctorScheduleRepository;

    public List<AppointmentDTO> getAppointmentByFilters(
            Boolean is_deleted,
            Date appointment_date,
            String appointment_status
    ){
        return appointmentRepository.findAppointmentsByFilters(is_deleted, appointment_date, appointment_status).stream()
                .map(AppointmentDTO::new)
                .collect(Collectors.toList());
    }

    public Optional<AppointmentDTO> getAppointmentById(int id_appointment) {
        return appointmentRepository.findAppointmentById(id_appointment)
                .map(AppointmentDTO::new);
    }

    public List<AppointmentDTO> getAppointmentsByPatient(int id_patient) {
        return appointmentRepository.findAppointmentsByPatientId(id_patient).stream()
                .map(AppointmentDTO::new)
                .collect(Collectors.toList());
    }

    public List<AppointmentDTO> getAppointmentsByMedicalService(int id_medical_service) {
        return appointmentRepository.findAppointmentsByMedicalServicesId(id_medical_service).stream()
                .map(AppointmentDTO::new)
                .collect(Collectors.toList());
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

        //verificare daca exista pacient cu id-ul primit
        Patient patient = patientRepository.findPatientById(appointmentDTO.getId_patient()).orElse(null);
        if(patient == null) {
            errors.append("Pacientul cu id-ul: ").append(appointmentDTO.getId_patient()).append(" nu exista.")
                    .append(System.lineSeparator());
        }

        //verificare daca exista serviciul medical cu id-ul primit
        MedicalServices medicalServices = medicalServicesRepository.findMedicalServicesById(appointmentDTO.getId_medical_service()).orElse(null);
        if(medicalServices == null) {
            errors.append("Serviciul Medical cu id-ul: ").append(appointmentDTO.getId_medical_service()).append(" nu exista.")
                    .append(System.lineSeparator());
        }

        //verificare daca introducem o data gresita
        if(ValidationUtils.validateDate(String.valueOf(appointmentDTO.getAppointment_date()))) {
            errors.append("Data: ").append(appointmentDTO.getAppointment_date())
                    .append(" nu este formatata corect.")
                    .append(System.lineSeparator());
        }
        //verificare daca doctorul are programul liber pentru acea programare
//        if(medicalServices !=null) {
//            AuxiliarMethods.checkDoctorSchedule(appointmentDTO, medicalServices, doctorScheduleRepository, errors);
//        }
        //lista programarilor existente
        List<AppointmentDTO> existingAppointments = getAppointmentByFilters(null, null,null);

        //verificare daca datele introduse nu exista deja (daca are acelasi pacient, acelasi serviciu medical si acceasi data)
        if(existingAppointments.stream().anyMatch(a ->
                a.getId_patient().equals(appointmentDTO.getId_patient()) &&
                a.getId_medical_service().equals(appointmentDTO.getId_medical_service()) &&
                a.getAppointment_date().equals(appointmentDTO.getAppointment_date()) && !a.getIs_deleted()
        )) {
            errors.append("Exista deja acest o programare pentru pacientul: ").append(appointmentDTO.getId_patient())
                    .append(", pentru serviciul medical: ").append(appointmentDTO.getId_medical_service())
                    .append(", pe data de: ").append(appointmentDTO.getAppointment_date())
                    .append(System.lineSeparator());
        }

        //afisarea erorilor
        if(!errors.isEmpty()) {
            throw new IllegalArgumentException(errors.toString().trim());
        }

        Appointment appointment = new Appointment(appointmentDTO);
        appointment.setPatient(patient);
        appointment.setMedicalService(medicalServices);
        appointment.setAppointment_status(AppointmentStatus.created);
        appointment.setCreated_at(new Date());
        appointment.setUpdated_at(null);
        appointment.setIs_deleted(false);

        Appointment savedAppointment = appointmentRepository.save(appointment);
        return new AppointmentDTO(savedAppointment);
    }

    public AppointmentDTO updateAppointment(Integer id_appointment, Map<String, Object> updates) {

        //verificari necesare
        StringBuilder errors = new StringBuilder();

        //verificare daca exista programarea mentionata - daca nu exista, nu mai continuam verificarile
        Appointment appointment = appointmentRepository.findAppointmentById(id_appointment)
                .orElseThrow(() -> new IllegalArgumentException("Nu a fost gasita programarea cu id-ul: " + id_appointment));

        //verificare daca nu se introduc date nule
        String emptyFieldsError = CheckFields.checkEmptyFields(
                DTOConverter.convertToMap(updates),
                Set.of("id_appointment"));
        if (emptyFieldsError != null) {
            errors.append(emptyFieldsError)
                    .append(System.lineSeparator());
        }

        //lista programarilor existente
        List<AppointmentDTO> existingAppointments = getAppointmentByFilters(null, null,null);

        updates.forEach((field,value) ->{
            switch (field){
                case "id_patient":
                    Integer id_patient = Integer.parseInt(value.toString());
                    Patient patient = patientRepository.findPatientById(id_patient).orElse(null);
                    if (patient == null) {
                        errors.append("Nu a fost gasit pacientul cu id-ul: ").append(id_patient)
                                .append(System.lineSeparator());
                    }
                    appointment.setPatient(patient);
                    break;
                case "id_medical_service":
                    Integer id_medical_service = Integer.parseInt(value.toString());
                    MedicalServices medicalServices = medicalServicesRepository.findMedicalServicesById(id_medical_service).orElse(null);
                    if (medicalServices == null) {
                        errors.append("Nu a fost gasit serviciul medical cu id-ul: ").append(id_medical_service)
                                .append(System.lineSeparator());
                    }
                    appointment.setMedicalService(medicalServices);
                    break;
                case "appointment_date":
                    if(ValidationUtils.validateDate((String) value)) {
                        errors.append("Data: ").append((String) value)
                                .append(" nu este formatata corect.")
                                .append(System.lineSeparator());
                    }
                    MedicalServices medicalServices1 = medicalServicesRepository.findMedicalServicesById(appointment.getMedicalService().getId_medical_service()).orElse(null);
                    //verificare daca doctorul are programul liber pentru acea programare
//                    if(medicalServices1 !=null) {
//                        AuxiliarMethods.checkDoctorSchedule(new AppointmentDTO(appointment), medicalServices1, doctorScheduleRepository, errors);
//                    }
                    break;
                case "appointment_status":
                    if (CheckFields.isValidEnumValue(
                            Stream.of(AppointmentStatus.values()).map(Enum::name).toList(),
                            (String) value)) {
                        errors.append("Statusul programarii: ")
                                .append(value)
                                .append(" este invalida.")
                                .append(System.lineSeparator());
                    }
                    appointment.setAppointment_status(AppointmentStatus.valueOf((String) value));
                    break;
                case "id_appointment":
                case "created_at":
                case "updated_at":
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

        //verificare daca datele introduse nu exista deja (daca are acelasi nume, acelasi tip si acelasi medic asignat)
        if(existingAppointments.stream().anyMatch(a ->
                a.getId_patient().equals(appointment.getPatient().getId_patient()) &&
                        a.getId_medical_service().equals(appointment.getMedicalService().getId_medical_service()) &&
                        a.getAppointment_date().equals(appointment.getAppointment_date()) && !a.getIs_deleted()
        )) {
            errors.append("Exista deja acest o programare pentru pacientul: ").append(appointment.getPatient().getId_patient())
                    .append(", pentru serviciul medical: ").append(appointment.getMedicalService().getId_medical_service())
                    .append(", pe data de: ").append(appointment.getAppointment_date())
                    .append(System.lineSeparator());
        }

        //afisarea erorilor
        if(!errors.isEmpty()) {
            throw new IllegalArgumentException(errors.toString().trim());
        }

        appointment.setUpdated_at(new Date());
        Appointment savedMedicalService = appointmentRepository.save(appointment);
        return new AppointmentDTO(savedMedicalService);
    }

    public void deleteAppointment(Integer id_appointment) {
        Appointment appointment = appointmentRepository.findAppointmentById(id_appointment)
                .orElseThrow(() -> new IllegalArgumentException("Nu a fost gasita programarea cu id-ul: " + id_appointment));

        if(appointment.getIs_deleted()){
            throw  new IllegalArgumentException("Aceasta programare a fost deja stearsa: " + id_appointment);
        }

        appointment.setIs_deleted(true);
        appointment.setUpdated_at(new Date());
        appointmentRepository.save(appointment);
    }
}
