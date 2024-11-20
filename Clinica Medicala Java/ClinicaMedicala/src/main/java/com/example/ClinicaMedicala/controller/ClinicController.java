package com.example.ClinicaMedicala.controller;

import com.example.ClinicaMedicala.dto.ClinicDTO;
import com.example.ClinicaMedicala.dto.UserDTO;
import com.example.ClinicaMedicala.service.ClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/clinica-medicala/clinics")
public class ClinicController {

    @Autowired
    private ClinicService clinicService;

    @GetMapping
    public ResponseEntity<?> getAllClinics() {
        List<ClinicDTO> clinics = clinicService.getAllClinics();
        return ResponseEntity.ok(clinics);
    }

    @PostMapping
    public ResponseEntity<?> addClinic(@RequestBody ClinicDTO clinicDTO) {
        ClinicDTO newClinicDTO = clinicService.addClinic(clinicDTO);
        return ResponseEntity.ok(newClinicDTO);
    }
}
