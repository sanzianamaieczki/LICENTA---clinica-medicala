package com.example.ClinicaMedicala.service;

import com.example.ClinicaMedicala.dto.SpecializationDTOComponent.SpecializationDTO;
import com.example.ClinicaMedicala.entity.SpecializationEntityComponents.Specialization;
import com.example.ClinicaMedicala.repository.SpecializationRepositoryComponents.SpecializationRepository;
import com.example.ClinicaMedicala.utils.CheckFields;
import com.example.ClinicaMedicala.utils.DTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SpecializationService {

    @Autowired
    private SpecializationRepository specializationRepository;

    public List<SpecializationDTO> getSpecializationsByFilters(
            Boolean is_deleted,
            String specialization_name){
        return specializationRepository.findSpecializationsByFilters(is_deleted, specialization_name).stream()
                .map(SpecializationDTO::new)
                .collect(Collectors.toList());
    }

    public Optional<SpecializationDTO> getSpecializationById(int id_specialization) {
        return specializationRepository.findSpecializationById(id_specialization)
                .map(SpecializationDTO::new);
    }

    public SpecializationDTO addSpecialization(SpecializationDTO specializationDTO) {

        //verificari necesare
        StringBuilder errors = new StringBuilder();

        //verificare daca datele introduse sunt nule
        String emptyFieldsError = CheckFields.checkEmptyFields(
                DTOConverter.convertToMap(specializationDTO),
                Set.of("id_specialization"));
        if (emptyFieldsError != null) {
            errors.append(emptyFieldsError)
                    .append(System.lineSeparator());
        }

        //lista specializarilor existenti
        List<SpecializationDTO> existingSpecializations = getSpecializationsByFilters(null,null);

        //verificare daca datele introduse nu exista deja
        if(existingSpecializations.stream().anyMatch(s->s.getSpecialization_name().equals(specializationDTO.getSpecialization_name()))) {
            errors.append("Exista deja acest nume: ").append(specializationDTO.getSpecialization_name())
                    .append(System.lineSeparator());
        }

        //afisarea erorilor
        if(!errors.isEmpty()) {
            throw new IllegalArgumentException(errors.toString().trim());
        }

        Specialization specialization = new Specialization(specializationDTO);
        specialization.setCreated_at(new Date());
        specialization.setUpdated_at(null);
        specialization.setIs_deleted(false);

        Specialization savedspecialization = specializationRepository.save(specialization);
        return new SpecializationDTO(savedspecialization);
    }

    public SpecializationDTO updateSpecialization(Integer id_specialization , Map<String, Object> updates) {

        //verificari necesare
        StringBuilder errors = new StringBuilder();

        //verificare daca datele introduse sunt nule
        String emptyFieldsError = CheckFields.checkEmptyFields(
                DTOConverter.convertToMap(updates),
                Set.of("id_specialization"));
        if (emptyFieldsError != null) {
            errors.append(emptyFieldsError)
                    .append(System.lineSeparator());
        }

        //lista specializarilor existenti
        List<SpecializationDTO> existingSpecializations = getSpecializationsByFilters(null,null);

        Specialization specialization = specializationRepository.findSpecializationById(id_specialization)
                .orElseThrow(() -> new IllegalArgumentException("Nu a fost gasita specializarea cu id-ul: " + id_specialization));

        updates.forEach((field, value) ->{
            switch (field){
                case "specialization_name":
                    if(existingSpecializations.stream().anyMatch(s->s.getSpecialization_name().equals(value))) {
                        errors.append("Exista deja acest nume: ").append(field)
                                .append(System.lineSeparator());
                    }
                    specialization.setSpecialization_name((String) value);
                    break;
                case "id_specialization":
                case "created_at":
                case "updated_at":
                case "is_deleted":
                    throw new IllegalArgumentException("Acest camp nu poate fi modificat: " + field);
                default:
                    throw new IllegalArgumentException("Acest camp nu exista: " + field);
            }
        });

        //afisarea erorilor
        if(!errors.isEmpty()) {
            throw new IllegalArgumentException(errors.toString().trim());
        }

        specialization.setUpdated_at(new Date());
        Specialization savedspecialization = specializationRepository.save(specialization);
        return new SpecializationDTO(savedspecialization);
    }

    public void deleteSpecialization(int id_specialization) {
        Specialization specialization = specializationRepository.findSpecializationById(id_specialization)
                .orElseThrow(() -> new IllegalArgumentException("Nu a fost gasita specializarea cu id-ul: " + id_specialization));

        if(specialization.getIs_deleted()){
            throw new IllegalArgumentException("Aceasta specializare a fost deja stearsa: " + id_specialization);
        }

        specialization.setIs_deleted(true);
        specialization.setUpdated_at(new Date());
        specializationRepository.save(specialization);
    }
}
