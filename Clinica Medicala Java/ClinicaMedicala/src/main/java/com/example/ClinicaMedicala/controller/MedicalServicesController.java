package com.example.ClinicaMedicala.controller;

import com.example.ClinicaMedicala.dto.MedicalServicesDTO;
import com.example.ClinicaMedicala.enums.MedicalServicesType;
import com.example.ClinicaMedicala.service.MedicalServicesService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/clinica-medicala/medical-services")
public class MedicalServicesController {

    @Autowired
    MedicalServicesService medicalServicesService;

    @GetMapping
    public ResponseEntity<?> getAllMedicalServices(
            @RequestParam(value = "is_deleted", required = false, defaultValue = "false") Boolean is_deleted,
            @RequestParam(value = "medical_service_name", required = false) String medicalServiceName,
            @RequestParam(value = "price", required = false) Double price,
            @RequestParam(value = "medical_service_type", required = false) String medical_service_type
    ) {
        try{
            List<MedicalServicesDTO> medicalServices = medicalServicesService.getMedicalServicesByFilters(is_deleted,medicalServiceName, price,medical_service_type);
            return ResponseEntity.status(HttpStatus.OK).body(medicalServices);
        } catch (HttpClientErrorException.UnprocessableEntity e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{id_medical_service}")
    public ResponseEntity<?> getMedicalServiceById(@PathVariable Integer id_medical_service) {
        try{
            Optional<MedicalServicesDTO> medicalServicesDTO = medicalServicesService.getMedicalServicesById(id_medical_service);
            return ResponseEntity.status(HttpStatus.OK).body(medicalServicesDTO);
        } catch (HttpClientErrorException.UnprocessableEntity e){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> addMedicalService(@RequestBody MedicalServicesDTO medicalServicesDTO) {
        try{
            MedicalServicesDTO medicalService = medicalServicesService.addMedicalService(medicalServicesDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(medicalService);
        }catch (HttpClientErrorException.UnprocessableEntity e){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
        }catch (HttpClientErrorException.Conflict e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PatchMapping("/{id_medical_service}")
    public ResponseEntity<?> updateMedicalService(@PathVariable Integer id_medical_service, @RequestBody Map<String, Object> updates) {
        try{
            MedicalServicesDTO medicalServicesDTO = medicalServicesService.updateMedicalService(id_medical_service, updates);
            return ResponseEntity.status(HttpStatus.OK).body(medicalServicesDTO);
        }catch (HttpClientErrorException.UnprocessableEntity e){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
        }catch (HttpClientErrorException.Conflict e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id_medical_service}")
    public ResponseEntity<?> deleteMedicalService(@PathVariable Integer id_medical_service) {
        try{
            medicalServicesService.deleteMedicalService(id_medical_service);
            return ResponseEntity.status(HttpStatus.OK).body("Serviciul cu id-ul: " + id_medical_service + " a fost sters.");
        }catch (HttpClientErrorException.UnprocessableEntity e){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
        }catch (HttpClientErrorException.Conflict e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
