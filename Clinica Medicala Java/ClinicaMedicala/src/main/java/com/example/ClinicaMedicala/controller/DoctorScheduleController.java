package com.example.ClinicaMedicala.controller;

import com.example.ClinicaMedicala.dto.DoctorScheduleDTO;
import com.example.ClinicaMedicala.service.DoctorScheduleService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.sql.Time;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/clinica-medicala/doctor-schedule")
public class DoctorScheduleController {

    @Autowired
    DoctorScheduleService doctorScheduleService;

    @GetMapping
    public ResponseEntity<?> getAllDoctorSchedule(
            @RequestParam(value = "is_deleted", required = false, defaultValue = "false") Boolean is_deleted,
            @RequestParam(value = "day_of_week", required = false) String day_of_week,
            @RequestParam(value = "start_time", required = false) Time start_time,
            @RequestParam(value = "end_time", required = false) Time end_time
    ) {
        try{
            List<DoctorScheduleDTO> doctorSchedule = doctorScheduleService.getDoctorScheduleByFilters(is_deleted,day_of_week, start_time, end_time);
            return ResponseEntity.status(HttpStatus.OK).body(doctorSchedule);
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

    @GetMapping("/{id_doctor_schedule}")
    public ResponseEntity<?> getDoctorScheduleById(@PathVariable Integer id_doctor_schedule) {
        try{
            Optional<DoctorScheduleDTO> doctorScheduleDTO = doctorScheduleService.getDoctorScheduleById(id_doctor_schedule);
            return ResponseEntity.status(HttpStatus.OK).body(doctorScheduleDTO);
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
    public ResponseEntity<?> addDoctorSchedule(@RequestBody DoctorScheduleDTO doctorScheduleDTO) {
        try{
            DoctorScheduleDTO doctorSchedule = doctorScheduleService.addDoctorSchedule(doctorScheduleDTO);
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

    @PatchMapping("/{id_doctor_schedule}")
    public ResponseEntity<?> updateDoctorSchedule(@PathVariable Integer id_doctor_schedule, @RequestBody Map<String, Object> updates) {
        try{
            DoctorScheduleDTO doctorScheduleDTO = doctorScheduleService.updateDoctorSchedule(id_doctor_schedule, updates);
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

    @DeleteMapping("/{id_doctor_schedule}")
    public ResponseEntity<?> deleteDoctorSchedule(@PathVariable Integer id_doctor_schedule) {
        try{
            doctorScheduleService.deleteDoctorSchedule(id_doctor_schedule);
            return ResponseEntity.status(HttpStatus.OK).body("Serviciul cu id-ul: " + id_doctor_schedule + " a fost sters.");
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
