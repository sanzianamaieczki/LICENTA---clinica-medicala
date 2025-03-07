package com.example.ClinicaMedicala.service;

import com.example.ClinicaMedicala.dto.PatientDTO;
import com.example.ClinicaMedicala.service.PatientServiceComponents.PatientComponents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PatientService {
    @Autowired
    private PatientComponents patientComponents;

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
        return patientComponents.getPatientsByFilters(is_deleted, first_name, last_name, email, phone, national_id, birth_date_start, birth_date_end);
    }

    public Optional<PatientDTO> getPatientById(int id_patient) {
        return patientComponents.getPatientById(id_patient);
    }

    public PatientDTO addPatient(PatientDTO patientDTO) {
        return patientComponents.addPatient(patientDTO);
    }

    public PatientDTO updatePatient(Integer id_patient, Map<String, Object> updates) {
        return patientComponents.updatePatient(id_patient, updates);
    }

    public void deletePatient(Integer id_patient) {
        patientComponents.deletePatient(id_patient);
    }
}
