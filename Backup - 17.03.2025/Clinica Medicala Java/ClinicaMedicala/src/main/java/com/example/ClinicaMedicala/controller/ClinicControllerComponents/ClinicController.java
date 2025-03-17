package com.example.ClinicaMedicala.controller.ClinicControllerComponents;

import com.example.ClinicaMedicala.dto.ClinicDTOComponents.ClinicDTO;
import com.example.ClinicaMedicala.dto.DoctorDTOComponents.DoctorDTO;
import com.example.ClinicaMedicala.service.ClinicService;
import com.example.ClinicaMedicala.service.DoctorService;
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
@RequestMapping("/api/clinica-medicala/clinics")
public class ClinicController {

    @Autowired
    private ClinicService clinicService;

    @Autowired
    private DoctorService doctorService;

    // GET - pentru a afisa toate clinicile
    // Daca dorim sa vedem si clinicile care au fost sterse, adaugam un parametru "is_deleted"
    @GetMapping
    public ResponseEntity<?> getAllClinics(
            @RequestParam(value = "is_deleted", required = false, defaultValue = "false") Boolean is_deleted,
            @RequestParam(value = "clinic_name", required = false) String clinic_name,
            @RequestParam(value = "clinic_address", required = false) String clinic_address,
            @RequestParam(value = "clinic_phone", required = false) String clinic_phone
    ) {
        try {
            List<ClinicDTO> clinics = clinicService.getClinicsByFilters(is_deleted, clinic_name, clinic_address, clinic_phone);
            return ResponseEntity.status(HttpStatus.OK).body(clinics);

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

    @GetMapping("/{id_clinic}")
    public ResponseEntity<?> getClinicById(@PathVariable Integer id_clinic) {
        try{
            Optional<ClinicDTO> clinicDTO = clinicService.getClinicById(id_clinic);
            return ResponseEntity.status(HttpStatus.OK).body(clinicDTO);
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

    @GetMapping("/{id_clinic}/doctors")
    public ResponseEntity<?> getDoctorsByClinic(@PathVariable Integer id_clinic) {
        try{
            List<DoctorDTO> doctors = doctorService.getDoctorsByClinic(id_clinic);
            return ResponseEntity.status(HttpStatus.OK).body(doctors);
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
    public ResponseEntity<?> addClinic(@RequestBody ClinicDTO clinicDTO) {
        try{
        ClinicDTO newClinicDTO = clinicService.addClinic(clinicDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(newClinicDTO);
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

    @PatchMapping("/{id_clinic}")
    public ResponseEntity<?> partialUpdateClinic(@PathVariable Integer id_clinic, @RequestBody Map<String, Object> updates) {
        try{
           ClinicDTO updatedClinicDTO = clinicService.partialUpdateClinic(id_clinic, updates);
           return ResponseEntity.status(HttpStatus.CREATED).body(updatedClinicDTO);
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

    @DeleteMapping("/{id_clinic}")
    public ResponseEntity<?> deleteClinic(@PathVariable Integer id_clinic) {
        try{
            clinicService.deleteClinic(id_clinic);
            return ResponseEntity.status(HttpStatus.OK).body("Clinica cu id-ul : " + id_clinic + " a fost stearsa");
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
