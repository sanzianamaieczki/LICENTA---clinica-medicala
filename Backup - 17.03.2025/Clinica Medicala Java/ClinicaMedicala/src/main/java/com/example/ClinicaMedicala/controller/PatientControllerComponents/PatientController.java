package com.example.ClinicaMedicala.controller.PatientControllerComponents;

import com.example.ClinicaMedicala.dto.AppointmentDTOComponents.AppointmentDTO;
import com.example.ClinicaMedicala.dto.PatientDTOComponents.PatientDTO;
import com.example.ClinicaMedicala.service.AppointmentService;
import com.example.ClinicaMedicala.service.PatientService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/clinica-medicala/patients")
public class PatientController {

    @Autowired
    PatientService patientService;
    @Autowired
    private AppointmentService appointmentService;

    @GetMapping
    public ResponseEntity<?> getAllPatients(
            @RequestParam(value = "is_deleted", required = false, defaultValue = "false") Boolean is_deleted,
            @RequestParam(value = "first_name", required = false) String first_name,
            @RequestParam(value = "last_name", required = false) String last_name,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "national_id", required = false) String national_id,
            @RequestParam(value = "birth_date_start", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date birth_date_start,
            @RequestParam(value = "birth_date_end", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date birth_date_end
    ) {
        try{
            List<PatientDTO> patients = patientService.getPatientsByFilters(is_deleted, first_name, last_name, email,phone,national_id,birth_date_start,birth_date_end);
            return ResponseEntity.status(HttpStatus.OK).body(patients);
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

    @GetMapping("/{id_patient}")
    public ResponseEntity<?> getPatientById(@PathVariable Integer id_patient) {
        try{
            Optional<PatientDTO> patientDTO = patientService.getPatientById(id_patient);
            return ResponseEntity.status(HttpStatus.OK).body(patientDTO);
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

    @GetMapping("/{id_patient}/appointments")
    public ResponseEntity<?> getAppointmentsByPatient(@PathVariable Integer id_patient) {
        try{
            List<AppointmentDTO> appointmentDTOS = appointmentService.getAppointmentsByPatient(id_patient);
            return ResponseEntity.status(HttpStatus.OK).body(appointmentDTOS);
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
    public ResponseEntity<?> addPatient(@RequestBody PatientDTO patientDTO) {
        try{
            PatientDTO patient = patientService.addPatient(patientDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(patient);
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

    @PatchMapping("/{id_patient}")
    public ResponseEntity<?> updatePatient(@PathVariable Integer id_patient, @RequestBody Map<String, Object> updates) {
        try{
            PatientDTO patientDTO = patientService.updatePatient(id_patient, updates);
            return ResponseEntity.status(HttpStatus.OK).body(patientDTO);
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

    @DeleteMapping("/{id_patient}")
    public ResponseEntity<?> deletePatient(@PathVariable Integer id_patient) {
        try{
            patientService.deletePatient(id_patient);
            return ResponseEntity.status(HttpStatus.OK).body("Pacientul cu id-ul: " + id_patient + " a fost sters.");
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
