package com.example.ClinicaMedicala.service;

import com.example.ClinicaMedicala.dto.ClinicDTO;
import com.example.ClinicaMedicala.entity.Clinic;
import com.example.ClinicaMedicala.repository.ClinicRepository;
import com.example.ClinicaMedicala.utils.CheckFields;
import com.example.ClinicaMedicala.utils.DTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClinicService {

    @Autowired
    private ClinicRepository clinicRepository;

    public List<ClinicDTO> getClinicsByFilters(
            Boolean is_deleted,
            String clinic_name,
            String clinic_address,
            String clinic_phone

    ){
        return clinicRepository.findClinicsByFilters(is_deleted, clinic_name, clinic_address, clinic_phone).stream()
                .map(ClinicDTO::new)
                .collect(Collectors.toList());
    }

    public Optional<ClinicDTO> getClinicById(int id_clinic) {
        return clinicRepository.findClinicById(id_clinic)
                .map(ClinicDTO::new);
    }

    public ClinicDTO addClinic(ClinicDTO clinicDTO) {

        //verificari necesare
        StringBuilder errors = new StringBuilder();

        //verificare daca datele introduse sunt nule
        String emptyFieldError = CheckFields.checkEmptyFields(
                DTOConverter.convertToMap(clinicDTO),
                Set.of("id_clinic"));
        if(emptyFieldError != null) {
            errors.append(emptyFieldError)
                    .append(System.lineSeparator());
        }

        //lista clinicilor existente
        List<ClinicDTO> existingClinic = getClinicsByFilters(null, null,null, null);

        //verificari pentru a nu adauga clinici deja existente
        if(existingClinic.stream().anyMatch(c -> c.getClinic_name().equalsIgnoreCase(clinicDTO.getClinic_name()))){
            errors.append("Exista deja o clinica cu acest nume: ").append(clinicDTO.getClinic_name())
                    .append(System.lineSeparator());
        }

        //afisarea erorilor
        if(!errors.isEmpty()){
            throw new IllegalArgumentException(errors.toString().trim());
        }

        Clinic clinic = new Clinic(clinicDTO);
        clinic.setCreated_at(new Date());
        clinic.setUpdated_at(null);
        clinic.setIs_deleted(false);

        Clinic savedClinic = clinicRepository.save(clinic);
        return new ClinicDTO(savedClinic);
    }

    public ClinicDTO partialUpdateClinic(Integer id_clinic, Map<String, Object> updates) {

        //verificari necesare
        StringBuilder errors = new StringBuilder();

        //verificare daca exista clinica mentionata - daca nu exista, nu mai continuam
        Clinic clinic = clinicRepository.findClinicById(id_clinic)
                .orElseThrow(() -> new IllegalArgumentException("Nu a fost gasita clinica cu id-ul: "+id_clinic));

        //verificare daca nu se introduc date nule
        String emptyFieldError = CheckFields.checkEmptyFields(
                DTOConverter.convertToMap(updates),
                Set.of("id_clinic"));
        if(emptyFieldError !=null) {
            errors.append(emptyFieldError)
                    .append(System.lineSeparator());
        }
        //lista clinicilor existente
        List<Clinic> existingClinic = clinicRepository.findClinicsByFilters(null, null, null, null);

        updates.forEach((field, value)->{
            switch (field) {
                case "clinic_name":
                    if(existingClinic.stream().anyMatch(c -> c.getClinic_name().equalsIgnoreCase((String) value))){
                        errors.append("Exista deja o clinica cu acest nume: ").append(value)
                                .append(System.lineSeparator());
                    }
                    clinic.setClinic_name((String) value);
                    break;
                case "clinic_address":
                    clinic.setClinic_address((String) value);
                    break;
                case "clinic_phone":
                    clinic.setClinic_phone((String) value);
                    break;
                case "id_clinic":
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

        //afisarea erorilor
        if(!errors.isEmpty()) {
            throw new IllegalArgumentException(errors.toString().trim());
        }

        clinic.setUpdated_at(new Date());
        Clinic updatedClinic = clinicRepository.save(clinic);
        return new ClinicDTO(updatedClinic);
    }

    public void deleteClinic(Integer id_clinic) {
        Clinic clinic = clinicRepository.findClinicById(id_clinic)
                .orElseThrow(() -> new IllegalArgumentException("Nu a fost gasita clinica cu id-ul: "+id_clinic));

        if(clinic.getIs_deleted()) {
            throw new IllegalArgumentException("Acesta clinica a fost deja stearsa: " + id_clinic);
        }

        clinic.setIs_deleted(true);
        clinic.setUpdated_at(new Date());
        clinicRepository.save(clinic);
    }
}
