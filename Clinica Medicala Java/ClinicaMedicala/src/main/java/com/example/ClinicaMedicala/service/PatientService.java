package com.example.ClinicaMedicala.service;

import com.example.ClinicaMedicala.dto.PatientDTO;
import com.example.ClinicaMedicala.entity.Patient;
import com.example.ClinicaMedicala.repository.PatientRepository;
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
        //to do: verificari daca sunt nule sau exista deja

        Patient patient = new Patient(patientDTO);

        patient.setBirth_date(extractBirthDateFromNationalId(patientDTO.getNational_id()));
        patient.setCreated_at(new Date());
        patient.setUpdated_at(null);
        patient.setIs_deleted(false);

        Patient savedPatient = patientRepository.save(patient);
        return new PatientDTO(savedPatient);
    }

    public PatientDTO updatePatient(Integer id_patient, Map<String, Object> updates) {
        //to do: verificari daca sunt nule sau exista deja

        Patient patient = patientRepository.findPatientById(id_patient)
                .orElseThrow(() -> new IllegalArgumentException("Nu a fost gasit patientul cu id-ul: " + id_patient));

        updates.forEach((field,value) ->{
            switch (field){
                case "first_name":
                    patient.setFirst_name((String) value);
                    break;
                case "last_name":
                    patient.setLast_name((String) value);
                    break;
                case "email":
                    patient.setEmail((String) value);
                    break;
                case "national_id":
                    patient.setNational_id((String)value);
                    patient.setBirth_date(extractBirthDateFromNationalId((String) value));
                    break;
                case "birth_date":
                case "id_patient":
                case "created_at":
                case "updated_at":
                case "is_deleted":
                    throw new IllegalArgumentException("Acest camp nu poate fi modificat: " + field);
                default:
                    throw new IllegalArgumentException("Acest camp nu exista: " + field);
            }
        });
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
