package com.example.ClinicaMedicala.service;

import com.example.ClinicaMedicala.dto.DoctorDTO;
import com.example.ClinicaMedicala.dto.PatientDTO;
import com.example.ClinicaMedicala.entity.Patient;
import com.example.ClinicaMedicala.repository.PatientRepository;
import com.example.ClinicaMedicala.utils.CheckFields;
import com.example.ClinicaMedicala.utils.DTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;

    public List<PatientDTO> getPatientsByFilters(
            Boolean is_deleted,
            String first_name,
            String last_name,
            String email,
            String phone,
            String national_id,
            Date birth_date_start,
            Date birth_date_end
    ){
        return patientRepository.findPatientsByFilters(is_deleted,first_name, last_name,email,phone,national_id, birth_date_start,birth_date_end).stream()
                .map(PatientDTO::new)
                .collect(Collectors.toList());
    }

    public Optional<PatientDTO> getPatientById(int id_patient) {
        return patientRepository.findPatientById(id_patient)
                .map(PatientDTO::new);
    }

    // creare automata a datei de nastere in functie de CNP-ul persoanei
    private Date extractBirthDateFromNationalId(String national_id) {
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

    public PatientDTO addPatient(PatientDTO patientDTO) {

        //verificari necesare
        StringBuilder errors = new StringBuilder();

        //verificare daca datele introduse sunt nule
        String emptyFieldsError = CheckFields.checkEmptyFields(
                DTOConverter.convertToMap(patientDTO),
                Set.of("id_patient", "birth_date"));
        if (emptyFieldsError != null) {
            errors.append(emptyFieldsError)
                    .append(System.lineSeparator());
        }

        //lista pacientilor existenti
        List<PatientDTO> existingPatients = getPatientsByFilters(null, null,null,null,null, null, null, null);

        //verificare daca datele introduse nu exista deja
        if(existingPatients.stream().anyMatch(p->p.getEmail().equals(patientDTO.getEmail()))){
            errors.append("Exista deja acest email: ").append(patientDTO.getEmail())
                    .append(System.lineSeparator());
        }
        if(existingPatients.stream().anyMatch(p->p.getPhone().equals(patientDTO.getPhone()))){
            errors.append("Exista deja acest numar de telefon: ").append(patientDTO.getPhone())
                    .append(System.lineSeparator());
        }
        if(existingPatients.stream().anyMatch(p->p.getNational_id().equals(patientDTO.getNational_id()))){
            errors.append("Exista deja acest CNP: ").append(patientDTO.getNational_id())
                    .append(System.lineSeparator());
        }

        //afisarea erorilor
        if(!errors.isEmpty()) {
            throw new IllegalArgumentException(errors.toString().trim());
        }

        Patient patient = new Patient(patientDTO);

        patient.setBirth_date(extractBirthDateFromNationalId(patientDTO.getNational_id()));
        patient.setCreated_at(new Date());
        patient.setUpdated_at(null);
        patient.setIs_deleted(false);

        Patient savedPatient = patientRepository.save(patient);
        return new PatientDTO(savedPatient);
    }

    public PatientDTO updatePatient(Integer id_patient, Map<String, Object> updates) {

        //verificari necesare
        StringBuilder errors = new StringBuilder();

        //verificare daca datele introduse sunt nule
        String emptyFieldsError = CheckFields.checkEmptyFields(
                DTOConverter.convertToMap(updates),
                Set.of("id_patient", "birth_date"));
        if (emptyFieldsError != null) {
            errors.append(emptyFieldsError)
                    .append(System.lineSeparator());
        }

        //lista pacientilor existenti
        List<PatientDTO> existingPatients = getPatientsByFilters(null, null,null,null,null, null, null, null);

        Patient patient = patientRepository.findPatientById(id_patient)
                .orElseThrow(() -> new IllegalArgumentException("Nu a fost gasit pacientul cu id-ul: " + id_patient));

        updates.forEach((field,value) ->{
            switch (field){
                case "first_name":
                    patient.setFirst_name((String) value);
                    break;
                case "last_name":
                    patient.setLast_name((String) value);
                    break;
                case "email":
                    if(existingPatients.stream().anyMatch(p->p.getEmail().equals(value))){
                        errors.append("Exista deja acest email: ").append(value)
                                .append(System.lineSeparator());
                    }
                    patient.setEmail((String) value);
                    break;
                case "national_id":
                    if(existingPatients.stream().anyMatch(p->p.getNational_id().equals(value))){
                        errors.append("Exista deja acest CNP: ").append(value)
                                .append(System.lineSeparator());
                    }
                    patient.setNational_id((String)value);
                    patient.setBirth_date(extractBirthDateFromNationalId((String) value));
                    break;
                case "phone":
                    if(existingPatients.stream().anyMatch(p->p.getPhone().equals(value))){
                        errors.append("Exista deja acest numar de telefon: ").append(value)
                                .append(System.lineSeparator());
                    }
                    patient.setPhone((String) value);
                case "birth_date":
                case "id_patient":
                case "created_at":
                case "updated_at":
                case "is_deleted":
                    errors.append("Acest camp nu poate fi modificat: ").append(field);
                    break;
                default:
                    errors.append("Acest camp nu exista: ").append(field);
                    break;
            }
        });

        //afisarea erorilor
        if(!errors.isEmpty()) {
            throw new IllegalArgumentException(errors.toString().trim());
        }

        patient.setUpdated_at(new Date());
        Patient savedPatient = patientRepository.save(patient);
        return new PatientDTO(savedPatient);
    }

    public void deletePatient(Integer id_patient) {
        Patient patient = patientRepository.findPatientById(id_patient)
                .orElseThrow(() -> new IllegalArgumentException("Nu a fost gasit patientul cu id-ul: " + id_patient));

        if(patient.getIs_deleted()){
            throw  new IllegalArgumentException("Acest patient a fost deja sters: " + id_patient);
        }

        patient.setIs_deleted(true);
        patient.setUpdated_at(new Date());
        patientRepository.save(patient);
    }
}
