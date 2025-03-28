package com.example.ClinicaMedicala.controller.AppointmentControllerComponents;

import com.example.ClinicaMedicala.dto.AppointmentDTOComponents.AppointmentDTO;
import com.example.ClinicaMedicala.service.AppointmentService;
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
@RequestMapping("/api/clinica-medicala/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping
    public ResponseEntity<?> getAllAppointments(
            @RequestParam(value = "is_deleted", required = false, defaultValue = "false") Boolean is_deleted,
            @RequestParam(value = "appointment_status", required = false) String appointment_status,
            @RequestParam(value = "appointment_date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date appointment_date
    ) {
        try {
            List<AppointmentDTO> appointments = appointmentService.getAppointmentsByFilters(is_deleted, appointment_status, appointment_date);
            return ResponseEntity.status(HttpStatus.OK).body(appointments);

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

    @GetMapping("/{id_appointment}")
    public ResponseEntity<?> getAppointmentById(@PathVariable Integer id_appointment) {
        try{
            Optional<AppointmentDTO> appointmentDTO = appointmentService.getAppointmentById(id_appointment);
            return ResponseEntity.status(HttpStatus.OK).body(appointmentDTO);
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
    public ResponseEntity<?> addAppointment(@RequestBody AppointmentDTO appointmentDTO) {
        try{
            AppointmentDTO newAppointmentDTO = appointmentService.addAppointment(appointmentDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(newAppointmentDTO);
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

    @PatchMapping("/{id_appointment}")
    public ResponseEntity<?> partialUpdateAppointment(@PathVariable Integer id_appointment, @RequestBody Map<String, Object> updates) {
        try{
            AppointmentDTO updatedAppointmentDTO = appointmentService.updateAppointment(id_appointment, updates);
            return ResponseEntity.status(HttpStatus.CREATED).body(updatedAppointmentDTO);
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

    @DeleteMapping("/{id_appointment}")
    public ResponseEntity<?> deleteAppointment(@PathVariable Integer id_appointment) {
        try{
            appointmentService.deleteAppointment(id_appointment);
            return ResponseEntity.status(HttpStatus.OK).body("Programarea cu id-ul : " + id_appointment + " a fost stearsa");
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
