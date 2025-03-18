package com.example.ClinicaMedicala.controller.DoctorControllerComponents;

import com.example.ClinicaMedicala.dto.DoctorDTOComponents.DoctorDTO;
import com.example.ClinicaMedicala.dto.DoctorDTOComponents.DoctorMedicalServicesDTO;
import com.example.ClinicaMedicala.dto.DoctorDTOComponents.DoctorScheduleDTO;
import com.example.ClinicaMedicala.dto.DoctorDTOComponents.MedicalServicesDTO;
import com.example.ClinicaMedicala.entity.DoctorEntityComponents.Doctor;
import com.example.ClinicaMedicala.entity.DoctorEntityComponents.DoctorMedicalServices;
import com.example.ClinicaMedicala.repository.DoctorRepositoryComponents.DoctorRepository;
import com.example.ClinicaMedicala.service.DoctorService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.print.Doc;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/clinica-medicala/doctors")
public class DoctorController {

    @Autowired
    DoctorService doctorService;
    @Autowired
    private DoctorRepository doctorRepository;

    @GetMapping
    public ResponseEntity<?> getAllDoctors(
            @RequestParam(value = "is_deleted", required = false, defaultValue = "false") Boolean is_deleted,
            @RequestParam(value = "first_name", required = false) String first_name,
            @RequestParam(value = "last_name", required = false) String last_name,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "phone", required = false) String phone
    ) {
        try{
            List<DoctorDTO> doctors = doctorService.getDoctorsByFilters(is_deleted, first_name, last_name, email, phone);
            return ResponseEntity.status(HttpStatus.OK).body(doctors);
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

    @GetMapping("/{id_doctor}")
    public ResponseEntity<?> getDoctorById(@PathVariable Integer id_doctor) {
        try{
            Optional<DoctorDTO> doctorDTO = doctorService.getDoctorById(id_doctor);
            return ResponseEntity.status(HttpStatus.OK).body(doctorDTO);
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

    @GetMapping("/medical-services")
    public ResponseEntity<?> getAllMedicalServices(
            @RequestParam(value = "is_deleted", required = false, defaultValue = "false") Boolean is_deleted,
            @RequestParam(value = "medical_service_name", required = false) String medicalServiceName,
            @RequestParam(value = "medical_service_type", required = false) String medical_service_type
    ) {
        try{
            List<MedicalServicesDTO> medicalServices = doctorService.getMedicalServicesByFilters(is_deleted,medicalServiceName,medical_service_type);
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

    @GetMapping("/{id_doctor}/medical-services")
    public ResponseEntity<?> getMedicalServicesByDoctor(@PathVariable Integer id_doctor) {
        try{
            Optional<Doctor> doctorOptional = doctorRepository.findDoctorById(id_doctor);
            if(!doctorOptional.isPresent()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doctor not found");
            }

            Doctor doctor = doctorOptional.get();

            List<DoctorMedicalServicesDTO> doctorMedicalServicesDTOS = doctor.getDoctorMedicalServices().stream()
                    .map(DoctorMedicalServicesDTO::new)
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.OK).body(doctorMedicalServicesDTOS);
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

    @GetMapping("/{id_doctor}/doctor-schedule")
    public ResponseEntity<?> getDoctorScheduleByDoctor(@PathVariable Integer id_doctor) {
        try{
            List<DoctorScheduleDTO> doctorSchedule = doctorService.getDoctorScheduleByDoctor(id_doctor);
            return ResponseEntity.status(HttpStatus.OK).body(doctorSchedule);
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
    public ResponseEntity<?> addDoctor(@RequestBody DoctorDTO doctorDTO) {
        try{
            DoctorDTO doctor = doctorService.addDoctor(doctorDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(doctor);
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

    @PatchMapping("/{id_doctor}")
    public ResponseEntity<?> updateDoctor(@PathVariable Integer id_doctor, @RequestBody Map<String, Object> updates) {
        try{
            DoctorDTO doctorDTO = doctorService.updateDoctor(id_doctor, updates);
            return ResponseEntity.status(HttpStatus.OK).body(doctorDTO);
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

    @DeleteMapping("/{id_doctor}")
    public ResponseEntity<?> deleteDoctor(@PathVariable Integer id_doctor) {
        try{
            doctorService.deleteDoctor(id_doctor);
            return ResponseEntity.status(HttpStatus.OK).body("Doctorul cu id-ul: " + id_doctor + " a fost sters.");
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
