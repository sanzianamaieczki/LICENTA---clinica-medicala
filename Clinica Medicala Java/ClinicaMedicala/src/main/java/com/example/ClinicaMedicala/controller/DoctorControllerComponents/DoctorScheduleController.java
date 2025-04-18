package com.example.ClinicaMedicala.controller.DoctorControllerComponents;

import com.example.ClinicaMedicala.dto.DoctorDTOComponents.DoctorScheduleDTO;
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
public class DoctorScheduleController {

    @Autowired
    DoctorService doctorService;

    @PostMapping("/{id_doctor}/doctor-schedule")
    public ResponseEntity<?> addDoctorSchedule(@PathVariable Integer id_doctor, @RequestBody DoctorScheduleDTO doctorScheduleDTO) {
        try{
            doctorScheduleDTO.setId_doctor(id_doctor);
            DoctorScheduleDTO doctorSchedule = doctorService.addDoctorSchedule(doctorScheduleDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(doctorSchedule);
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

    @PatchMapping("/{id_doctor}/doctor-schedule/{id_doctor_schedule}")
    public ResponseEntity<?> updateDoctorSchedule(@PathVariable Integer id_doctor, @PathVariable Integer id_doctor_schedule,@RequestBody Map<String, Object> updates) {
        try{
            DoctorScheduleDTO doctorScheduleDTO = doctorService.updateDoctorSchedule(id_doctor_schedule,id_doctor, updates);
            doctorScheduleDTO.setId_doctor(id_doctor);
            doctorScheduleDTO.setId_doctor_schedule(id_doctor_schedule);
            return ResponseEntity.status(HttpStatus.OK).body(doctorScheduleDTO);
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

    @DeleteMapping("/{id_doctor}/doctor-schedule/{id_doctor_schedule}")
    public ResponseEntity<?> deleteDoctorSchedule(@PathVariable Integer id_doctor_schedule, @PathVariable Integer id_doctor) {
        try{
            doctorService.deleteDoctorSchedule(id_doctor_schedule, id_doctor);
            return ResponseEntity.status(HttpStatus.OK).body("Programul cu id-ul: " + id_doctor_schedule + " a fost sters.");
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
