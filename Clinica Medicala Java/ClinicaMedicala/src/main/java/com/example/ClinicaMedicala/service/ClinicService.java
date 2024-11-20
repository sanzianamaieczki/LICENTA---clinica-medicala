package com.example.ClinicaMedicala.service;

import com.example.ClinicaMedicala.dto.ClinicDTO;
import com.example.ClinicaMedicala.entity.Clinic;
import com.example.ClinicaMedicala.repository.ClinicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClinicService {

    @Autowired
    private ClinicRepository clinicRepository;

    public List<ClinicDTO> getAllClinics() {
        return clinicRepository.findAll().stream()
                .map(ClinicDTO::new)
                .collect(Collectors.toList());
    }
    public ClinicDTO addClinic(ClinicDTO clinicDTO) {

//        boolean exists = clinicRepository.existsByClinicName(clinicDTO.getClinic_name());
//        if (exists) {
//            throw new IllegalArgumentException("Clinica cu acest nume deja exista!");
//        }
        Clinic clinic = new Clinic(clinicDTO);
        Clinic savedClinic = clinicRepository.save(clinic);

        return new ClinicDTO(savedClinic);
    }
}
