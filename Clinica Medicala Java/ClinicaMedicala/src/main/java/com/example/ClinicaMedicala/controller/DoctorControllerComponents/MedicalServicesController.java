package com.example.ClinicaMedicala.controller.DoctorControllerComponents;

import com.example.ClinicaMedicala.dto.DoctorDTOComponents.MedicalServicesDTO;
import com.example.ClinicaMedicala.service.DoctorService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/clinica-medicala/doctors")
public class MedicalServicesController {

    @Autowired
    DoctorService doctorService;

    @GetMapping("/medical-services")
    public ResponseEntity<?> getAllMedicalServices(
            @RequestParam(value = "is_deleted", required = false, defaultValue = "false") Boolean is_deleted,
            @RequestParam(value = "medical_service_name", required = false) String medicalServiceName
    ) {
        try{
            List<MedicalServicesDTO> medicalServices = doctorService.getMedicalServicesByFilters(is_deleted,medicalServiceName);
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

    @PostMapping("/medical-services")
    public ResponseEntity<?> addMedicalService(@RequestBody MedicalServicesDTO medicalServicesDTO) {
        try{
            MedicalServicesDTO medicalService = doctorService.addMedicalService(medicalServicesDTO);
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

    @PatchMapping("/medical-services/{id_medical_service}")
    public ResponseEntity<?> updateMedicalService(@PathVariable Integer id_medical_service, @RequestBody Map<String, Object> updates) {
        try{
            MedicalServicesDTO medicalService = doctorService.updateMedicalService(id_medical_service,updates);
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

    @DeleteMapping("/medical-services/{id_medical_service}")
    public ResponseEntity<?> deleteMedicalService(@PathVariable Integer id_medical_service) {
        try{
            doctorService.deleteMedicalService(id_medical_service);
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
