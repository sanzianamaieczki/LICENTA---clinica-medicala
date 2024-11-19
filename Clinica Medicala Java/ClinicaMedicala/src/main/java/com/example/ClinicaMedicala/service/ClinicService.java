package com.example.ClinicaMedicala.service;

import com.example.ClinicaMedicala.dto.ClinicDTO;
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
}
