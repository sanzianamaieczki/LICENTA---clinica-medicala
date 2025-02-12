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

//    private CheckFields checkFields = new CheckFields();

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
        Map<String, Object> doctorFields = DTOConverter.convertToMap(doctorDTO);
        errors.append(CheckFields.checkEmptyFields(doctorFields,Set.of("id_doctor")));

        //verificare daca datele introduse nu exista deja
        List<Doctor> existingDoctors = doctorRepository.findDoctorsByFilters(null, null,null,null,null);
        if(existingDoctors.stream().anyMatch(d -> d.getEmail().equals(doctorDTO.getEmail()))) {
            errors.append("Exista deja acest email: ").append(doctorDTO.getEmail()).append(System.lineSeparator());
        }
        if(existingDoctors.stream().anyMatch(d -> d.getPhone().equals(doctorDTO.getPhone()))) {
            errors.append("Exista deja acest numar de telefon: ").append(doctorDTO.getPhone()).append(System.lineSeparator());
        }

        //afisarea erorilor
        if(!errors.isEmpty()) {
            throw new IllegalArgumentException(errors.toString().trim());
        }

        Doctor doctor = new Doctor(doctorDTO);

        Clinic clinic = clinicRepository.findById(doctorDTO.getId_clinic())
                .orElseThrow(() -> new IllegalArgumentException("Clinica cu id-ul " + doctorDTO.getId_clinic() + " nu exista."));

        Specialization specialization = specializationRepository.findById(doctorDTO.getId_specialization())
                .orElseThrow(() -> new IllegalArgumentException("Specializarea cu id-ul " + doctorDTO.getId_specialization() + " nu exista."));

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

        //verificare daca exista doctorul mentionat
        Doctor doctor = doctorRepository.findDoctorById(id_doctor)
                .orElseThrow(() -> new IllegalArgumentException("Nu a fost gasit doctorul cu id-ul: " + id_doctor));

        //verificare daca nu se introduc date nule
        errors.append(CheckFields.checkEmptyFields(updates, Set.of("id_doctor")));

        //afisarea erorilor
        if(!errors.isEmpty()) {
            throw new IllegalArgumentException(errors.toString().trim());
        }

        //to do: verificare daca nu cumva exista acel nume

        updates.forEach((field,value) ->{
            switch (field){
                case "first_name":
                    doctor.setFirst_name((String) value);
                    break;
                case "last_name":
                    doctor.setLast_name((String) value);
                    break;
                case "email":
                    doctor.setEmail((String) value);
                    break;
                case "id_clinic":
                    Integer id_clinic = (Integer) value;
                    Clinic clinic = clinicRepository.findClinicById(id_clinic)
                            .orElseThrow(()->new IllegalArgumentException("Nu a fost gasita clinica cu id-ul: " + id_clinic));
                    doctor.setClinic(clinic);
                    break;
                case "id_specialization":
                    Integer id_specialization = (Integer) value;
                    Specialization specialization = specializationRepository.findSpecializationById(id_specialization)
//                            .orElse(errors.append("Nu a fost gasita specializarea cu id-ul: ").append(id_specializati)
                            .orElseThrow(()->new IllegalArgumentException("Nu a fost gasita specializarea cu id-ul: " + id_specialization));
                    doctor.setSpecialization(specialization);
                    break;
                case "id_doctor":
                case "created_at":
                case "updated_at":
                case "is_deleted":
                    throw new IllegalArgumentException("Acest camp nu poate fi modificat: " + field);
                default:
                    throw new IllegalArgumentException("Acest camp nu exista: " + field);
            }
        });

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
