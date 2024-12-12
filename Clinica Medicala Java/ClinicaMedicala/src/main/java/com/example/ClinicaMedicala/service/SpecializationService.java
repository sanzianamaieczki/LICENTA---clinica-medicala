package com.example.ClinicaMedicala.service;

import com.example.ClinicaMedicala.dto.ClinicDTO;
import com.example.ClinicaMedicala.dto.SpecializationDTO;
import com.example.ClinicaMedicala.entity.Specialization;
import com.example.ClinicaMedicala.repository.SpecializationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SpecializationService {

    @Autowired
    private SpecializationRepository specializationRepository;

    public List<SpecializationDTO> getAllSpecializations() {
        return specializationRepository.findAll().stream()
                .map(SpecializationDTO::new)
                .collect(Collectors.toList());
    }

    public List<SpecializationDTO> getAllDeletedSpecializations() {
        return specializationRepository.findAllDeletedSpecializations().stream()
                .map(SpecializationDTO::new)
                .collect(Collectors.toList());
    }

    public Optional<SpecializationDTO> getSpecializationById(int id_specialization) {
        return specializationRepository.findSpecializationById(id_specialization)
                .map(SpecializationDTO::new);
    }

    public List<SpecializationDTO> getSpecializationByName(String specialization_name) {
        return specializationRepository.findSpecializationByName(specialization_name).stream()
                .map(SpecializationDTO::new)
                .collect(Collectors.toList());
    }

    public SpecializationDTO addSpecialization(SpecializationDTO specializationDTO) {

        Specialization specialization = new Specialization(specializationDTO);
        specialization.setCreated_at(new Date());
        specialization.setUpdated_at(null);
        specialization.setIs_deleted(false);

        Specialization savedspecialization = specializationRepository.save(specialization);
        return new SpecializationDTO(savedspecialization);
    }

    public SpecializationDTO updateSpecialization(Integer id_specialization , Map<String, Object> updates) {
        Specialization specialization = specializationRepository.findSpecializationById(id_specialization)
                .orElseThrow(() -> new IllegalArgumentException("Nu a fost gasita specializarea cu id-ul: " + id_specialization));

        updates.forEach((field, value) ->{
            switch (field){
                case "specialization_name":
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
