package com.example.ClinicaMedicala.service;

import com.example.ClinicaMedicala.dto.DoctorDTO;
import com.example.ClinicaMedicala.dto.SpecializationDTO;
import com.example.ClinicaMedicala.entity.Clinic;
import com.example.ClinicaMedicala.entity.Doctor;
import com.example.ClinicaMedicala.entity.Specialization;
import com.example.ClinicaMedicala.repository.ClinicRepository;
import com.example.ClinicaMedicala.repository.DoctorRepository;
import com.example.ClinicaMedicala.repository.SpecializationRepository;
import com.example.ClinicaMedicala.utils.CheckFields;
import com.example.ClinicaMedicala.utils.DTOConverter;
import com.fasterxml.jackson.databind.util.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.Set;


@Service
public class DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private ClinicRepository clinicRepository;
    @Autowired
    private SpecializationRepository specializationRepository;

    //lista doctorilor existenti
    List<Doctor> existingDoctors = doctorRepository.findDoctorsByFilters(null, null,null,null,null);

    public List<DoctorDTO> getDoctorsByFilters(
            Boolean is_deleted,
            String first_name,
            String last_name,
            String email,
            String phone
    ){
        return doctorRepository.findDoctorsByFilters(is_deleted,first_name, last_name,email, phone).stream()
                .map(DoctorDTO::new)
                .collect(Collectors.toList());
    }

    public Optional<DoctorDTO> getDoctorById(int id_doctor) {
        return doctorRepository.findDoctorById(id_doctor)
                .map(DoctorDTO::new);
    }

    public List<DoctorDTO> getDoctorsBySpecialization(int id_specialization) {
        return doctorRepository.findDoctorsBySpecializationId(id_specialization).stream()
                .map(DoctorDTO::new)
                .collect(Collectors.toList());
    }

    public List<DoctorDTO> getDoctorsByClinic(int id_clinic) {
        return doctorRepository.findDoctorsByClinicId(id_clinic).stream()
                .map(DoctorDTO::new)
                .collect(Collectors.toList());
    }

    public DoctorDTO addDoctor(DoctorDTO doctorDTO) {

        //verificari necesare
        StringBuilder errors = new StringBuilder();

        //verificare daca datele introduse sunt nule
        errors.append(CheckFields.checkEmptyFields(
                DTOConverter.convertToMap(doctorDTO),
                Set.of("id_doctor")))
                .append(System.lineSeparator());

        //verificare daca exista clinica cu id-ul primit
        Clinic clinic = clinicRepository.findById(doctorDTO.getId_clinic()).orElse(null);
        if(clinic == null) {
            errors.append("Clinica cu id-ul: ").append(doctorDTO.getId_clinic()).append(" nu exista.")
                    .append(System.lineSeparator());
        }

        //verificare daca exista specializarea cu id-ul primit
        Specialization specialization =specializationRepository.findById(doctorDTO.getId_specialization()).orElse(null);
        if(specialization == null) {
            errors.append("Specializarea cu id-ul ").append(doctorDTO.getId_specialization()).append(" nu exista.")
                    .append(System.lineSeparator());
        }

        //verificare daca datele introduse nu exista deja
        if(existingDoctors.stream().anyMatch(d -> d.getEmail().equals(doctorDTO.getEmail()))) {
            errors.append("Exista deja acest email: ").append(doctorDTO.getEmail())
                    .append(System.lineSeparator());
        }
        if(existingDoctors.stream().anyMatch(d -> d.getPhone().equals(doctorDTO.getPhone()))) {
            errors.append("Exista deja acest numar de telefon: ").append(doctorDTO.getPhone())
                    .append(System.lineSeparator());
        }

        //afisarea erorilor
        if(!errors.isEmpty()) {
            throw new IllegalArgumentException(errors.toString().trim());
        }

        Doctor doctor = new Doctor(doctorDTO);
        doctor.setClinic(clinic);
        doctor.setSpecialization(specialization);
        doctor.setCreated_at(new Date());
        doctor.setUpdated_at(null);
        doctor.setIs_deleted(false);

        Doctor savedDoctor = doctorRepository.save(doctor);
        return new DoctorDTO(savedDoctor);
    }

    public DoctorDTO updateDoctor(Integer id_doctor, Map<String, Object> updates) {

        //verificari necesare
        StringBuilder errors = new StringBuilder();

        //verificare daca exista doctorul mentionat - daca nu exista, nu mai continuam verificarile
        Doctor doctor = doctorRepository.findDoctorById(id_doctor)
                .orElseThrow(() -> new IllegalArgumentException("Nu a fost gasit doctorul cu id-ul: " + id_doctor));

        //verificare daca nu se introduc date nule
        errors.append(CheckFields.checkEmptyFields(updates, Set.of("id_doctor")));

        updates.forEach((field,value) ->{
            switch (field){
                case "first_name":
                    doctor.setFirst_name((String) value);
                    break;
                case "last_name":
                    doctor.setLast_name((String) value);
                    break;
                case "email":
                    if(existingDoctors.stream().anyMatch(d -> d.getEmail().equals(value))) {
                        errors.append("Exista deja acest email: ").append(value).append(System.lineSeparator());
                    }
                    doctor.setEmail((String) value);
                    break;
                case "phone":
                    if(existingDoctors.stream().anyMatch(d -> d.getPhone().equals(value))) {
                        errors.append("Exista deja acest numar de telefon: ").append(value).append(System.lineSeparator());
                    }
                    doctor.setPhone((String) value);
                case "id_clinic":
                    Integer id_clinic = (Integer) value;
                    Clinic clinic = clinicRepository.findClinicById(id_clinic).orElse(null);
                    if(clinic == null) {
                        errors.append("Nu a fost gasita clinica cu id-ul: ").append(id_clinic)
                                .append(System.lineSeparator());
                    }
                    doctor.setClinic(clinic);
                    break;
                case "id_specialization":
                    Integer id_specialization = (Integer) value;
                    Specialization specialization = specializationRepository.findSpecializationById(id_specialization).orElse(null);
                    if(specialization == null) {
                        errors.append("Nu a fost gasita specializarea cu id-ul: ").append(id_specialization)
                                .append(System.lineSeparator());
                    }
                    doctor.setSpecialization(specialization);
                    break;
                case "id_doctor":
                case "created_at":
                case "updated_at":
                case "is_deleted":
                    errors.append("Acest camp nu poate fi modificat").append(field)
                            .append(System.lineSeparator());
                default:
                    errors.append("Acest camp nu exista: ").append(field)
                            .append(System.lineSeparator());
            }
        });

        //afisarea erorilor
        if(!errors.isEmpty()) {
            throw new IllegalArgumentException(errors.toString().trim());
        }

        doctor.setUpdated_at(new Date());
        Doctor savedDoctor = doctorRepository.save(doctor);
        return new DoctorDTO(savedDoctor);
    }

    public void deleteDoctor(Integer id_doctor) {
        Doctor doctor = doctorRepository.findDoctorById(id_doctor)
                .orElseThrow(() -> new IllegalArgumentException("Nu a fost gasit doctorul cu id-ul: " + id_doctor));

        if(doctor.getIs_deleted()){
            throw  new IllegalArgumentException("Acest doctor a fost deja sters: " + id_doctor);
        }

        doctor.setIs_deleted(true);
        doctor.setUpdated_at(new Date());
        doctorRepository.save(doctor);
    }
}
