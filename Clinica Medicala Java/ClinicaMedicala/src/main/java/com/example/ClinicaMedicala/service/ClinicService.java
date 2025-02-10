package com.example.ClinicaMedicala.service;

import com.example.ClinicaMedicala.dto.ClinicDTO;
import com.example.ClinicaMedicala.entity.Clinic;
import com.example.ClinicaMedicala.repository.ClinicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClinicService {

    @Autowired
    private ClinicRepository clinicRepository;

    public List<ClinicDTO> getClinicsByFilters(
            Boolean is_deleted,
            String clinic_name,
            String clinic_address
    ){
        return clinicRepository.findClinicsByFilters(is_deleted, clinic_name, clinic_address).stream()
                .map(ClinicDTO::new)
                .collect(Collectors.toList());
    }

    public Optional<ClinicDTO> getClinicById(int id_clinic) {
        return clinicRepository.findClinicById(id_clinic)
                .map(ClinicDTO::new);
    }

    public ClinicDTO addClinic(ClinicDTO clinicDTO) {

        //verificari pentru a nu introduce date nule pentru clinici
        if(clinicDTO.getClinic_name() == null || clinicDTO.getClinic_name().isEmpty()){
            throw new IllegalArgumentException("Clinica trebuie sa aiba un nume nenul.");
        }
        if(clinicDTO.getClinic_address() == null || clinicDTO.getClinic_address().isEmpty()){
            throw new IllegalArgumentException("Clinica trebuie sa aiba o adresa nenula.");
        }
        if(clinicDTO.getClinic_phone() == null || clinicDTO.getClinic_phone().isEmpty()){
            throw new IllegalArgumentException("Clinica trebuie sa aiba un numar de telefon nenul.");
        }

        //verificari pentru a nu adauga clinici deja existente
        List<Clinic> existingClinic = clinicRepository.findClinicsByFilters(false, clinicDTO.getClinic_name(), null)
                .stream()
                .collect(Collectors.toList());
        if(!existingClinic.isEmpty()){
            throw new IllegalArgumentException("Clinica cu acest nume: "+ clinicDTO.getClinic_name() + " exista deja.");
        }

        Clinic clinic = new Clinic(clinicDTO);
        clinic.setCreated_at(new Date());
        clinic.setUpdated_at(null);
        clinic.setIs_deleted(false);

        Clinic savedClinic = clinicRepository.save(clinic);
        return new ClinicDTO(savedClinic);
    }

    public ClinicDTO partialUpdateClinic(Integer id_clinic, Map<String, Object> updates) {
        Clinic clinic = clinicRepository.findClinicById(id_clinic)
                .orElseThrow(() -> new IllegalArgumentException("Nu a fost gasita clinica cu id-ul: "+id_clinic));



        updates.forEach((field, value)->{
            switch (field) {
                case "clinic_name":
                    if(value == null){
                        throw new IllegalArgumentException("Clinica trebuie sa aiba un nume nenul.");
                    }
                     // to do: verificare daca exista deja
                    clinic.setClinic_name((String) value);
                    break;
                case "clinic_address":
                    if(value == null){
                        throw new IllegalArgumentException("Clinica trebuie sa aiba o adresa nenula.");
                    }
                    clinic.setClinic_address((String) value);
                    break;
                case "clinic_phone":
                    if(value == null){
                        throw new IllegalArgumentException("Clinica trebuie sa aiba un numar de telefon nenul.");
                    }
                    clinic.setClinic_phone((String) value);
                    break;
                case "id_clinic":
                case "created_at":
                case "updated_at":
                case "is_deleted":
                    throw new IllegalArgumentException("Acest camp nu poate fi modificat: " + field);
                default:
                    throw new IllegalArgumentException("Acest camp nu exista: " + field);
            }
        });

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
