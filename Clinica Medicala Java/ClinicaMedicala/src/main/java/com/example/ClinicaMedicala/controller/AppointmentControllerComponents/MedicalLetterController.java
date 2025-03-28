package com.example.ClinicaMedicala.controller.AppointmentControllerComponents;

import com.example.ClinicaMedicala.dto.AppointmentDTOComponents.MedicalLetterDTO;
import com.example.ClinicaMedicala.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/clinica-medicala/appointments")
public class MedicalLetterController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/{id_appointment}/medical-letter")
    public ResponseEntity<?> addMedicalLetter(@PathVariable Integer id_appointment, @RequestBody MedicalLetterDTO medicalLetterDTO) {
        try{
            medicalLetterDTO.setId_appointment(id_appointment);
            MedicalLetterDTO newMedicalLetterDTO = appointmentService.addMedicalLetter(medicalLetterDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(newMedicalLetterDTO);
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

    @PatchMapping("/{id_appointment}/medical-letter/{id_medical_letter}")
    public ResponseEntity<?> updateMedicalLetter(@PathVariable Integer id_appointment, @PathVariable Integer id_medical_letter, @RequestBody Map<String, Object> updates) {
        try{
            MedicalLetterDTO updatedMedicalLetterDTO = appointmentService.updateMedicalLetter(id_appointment, id_medical_letter, updates);
            return ResponseEntity.status(HttpStatus.CREATED).body(updatedMedicalLetterDTO);
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

    @DeleteMapping("/{id_appointment}/medical-letter/{id_medical_letter}")
    public ResponseEntity<?> deleteAppointment(@PathVariable Integer id_appointment, @PathVariable Integer id_medical_letter) {
        try{
            appointmentService.deleteMedicalLetter(id_appointment, id_medical_letter);
            return ResponseEntity.status(HttpStatus.OK).body("Scrisoarea medicala cu id-ul : " + id_medical_letter + " a fost stearsa");
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
