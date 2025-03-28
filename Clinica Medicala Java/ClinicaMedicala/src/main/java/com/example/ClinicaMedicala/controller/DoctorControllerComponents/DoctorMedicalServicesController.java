package com.example.ClinicaMedicala.controller.DoctorControllerComponents;

import com.example.ClinicaMedicala.dto.DoctorDTOComponents.DoctorMedicalServicesDTO;
import com.example.ClinicaMedicala.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/clinica-medicala/doctors")
public class DoctorMedicalServicesController {

    @Autowired
    DoctorService doctorService;

    @PostMapping("/{id_doctor}/medical-services")
    public ResponseEntity<?> addDoctorMedicalService(@PathVariable Integer id_doctor,@RequestBody DoctorMedicalServicesDTO doctorMedicalServicesDTO) {
        try{
            doctorMedicalServicesDTO.setId_doctor(id_doctor);
            DoctorMedicalServicesDTO medicalService = doctorService.addDoctorMedicalService(doctorMedicalServicesDTO);
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
    @PatchMapping("/{id_doctor}/medical-services/{id_doctor_medical_service}")
    public ResponseEntity<?> updateDoctorMedicalService(@PathVariable Integer id_doctor_medical_service,@PathVariable Integer id_doctor, @RequestBody Map<String, Object> updates) {
        try{
            DoctorMedicalServicesDTO doctorMedicalServicesDTO = doctorService.updateDoctorMedicalService(id_doctor_medical_service,id_doctor, updates);
            doctorMedicalServicesDTO.setId_doctor(id_doctor);
            doctorMedicalServicesDTO.setId_doctor_medical_service(id_doctor_medical_service);
            return ResponseEntity.status(HttpStatus.OK).body(doctorMedicalServicesDTO);
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

    @DeleteMapping("/{id_doctor}/medical-services/{id_doctor_medical_service}")
    public ResponseEntity<?> deleteDoctorMedicalService(@PathVariable Integer id_doctor_medical_service, @PathVariable Integer id_doctor) {
        try{
            doctorService.deleteDoctorMedicalService(id_doctor_medical_service,id_doctor);
            return ResponseEntity.status(HttpStatus.OK).body("Serviciul medical cu id-ul: " + id_doctor_medical_service + " a fost sters.");
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
