package com.example.ClinicaMedicala.controller.SpecializationControllerComponents;

import com.example.ClinicaMedicala.dto.SpecializationDTOComponent.SpecializationDTO;
import com.example.ClinicaMedicala.service.SpecializationService;
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
@RequestMapping("/api/clinica-medicala/specializations")
public class SpecializationController {

    @Autowired
    private SpecializationService specializationService;

    @GetMapping
    public ResponseEntity<?> getSpecializationsByFilters(
            @RequestParam(value = "is_deleted", required = false, defaultValue = "false") Boolean is_deleted,
            @RequestParam(value = "specialization_name", required = false) String specialization_name
    ) {
        try{
            List<SpecializationDTO> specializations = specializationService.getSpecializationsByFilters(is_deleted, specialization_name);
            return new ResponseEntity<>(specializations, HttpStatus.OK);
        }catch (HttpClientErrorException.UnprocessableEntity e){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{id_specialization}")
    public ResponseEntity<?> getSpecializationById(@PathVariable Integer id_specialization) {
        try{
            Optional<SpecializationDTO> specialization = specializationService.getSpecializationById(id_specialization);
            return ResponseEntity.status(HttpStatus.OK).body(specialization);
        }catch (HttpClientErrorException.UnprocessableEntity e){
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
    public ResponseEntity<?> addSpecialization(@RequestBody SpecializationDTO specializationDTO) {
        try{
            SpecializationDTO newSpecializationDTO = specializationService.addSpecialization(specializationDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(newSpecializationDTO);
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

    @PatchMapping("/{id_specialization}")
    public ResponseEntity<?> updateSpecialization(@PathVariable Integer id_specialization, @RequestBody Map<String, Object> updates) {
        try{
            SpecializationDTO updatedSpecializationDTO = specializationService.updateSpecialization(id_specialization, updates);
            return ResponseEntity.status(HttpStatus.CREATED).body(updatedSpecializationDTO);
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

    @DeleteMapping("/{id_specialization}")
    public ResponseEntity<?> deleteSpecialization(@PathVariable Integer id_specialization) {
        try{
            specializationService.deleteSpecialization(id_specialization);
            return ResponseEntity.status(HttpStatus.OK).body("Specializarea cu id-ul : " + id_specialization + " a fost stearsa");
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
