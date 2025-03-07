package com.example.ClinicaMedicala.service.DoctorServiceComponents;

import com.example.ClinicaMedicala.dto.DoctorDTO;
import com.example.ClinicaMedicala.entity.Clinic;
import com.example.ClinicaMedicala.entity.Doctor;
import com.example.ClinicaMedicala.entity.Specialization;
import com.example.ClinicaMedicala.repository.ClinicRepository;
import com.example.ClinicaMedicala.repository.DoctorRepository;
import com.example.ClinicaMedicala.repository.SpecializationRepository;
import com.example.ClinicaMedicala.utils.CheckFields;
import com.example.ClinicaMedicala.utils.DTOConverter;
import com.example.ClinicaMedicala.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DoctorComponents {
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private ClinicRepository clinicRepository;
    @Autowired
    private SpecializationRepository specializationRepository;

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
        String emptyFieldsError = CheckFields.checkEmptyFields(
                DTOConverter.convertToMap(doctorDTO),
                Set.of("id_doctor"));
        if (emptyFieldsError != null) {
            errors.append(emptyFieldsError)
                    .append(System.lineSeparator());
        }

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

        if(ValidationUtils.validatePhoneNumber(doctorDTO.getPhone())) {
            errors.append("Numarul de telefon: ").append(doctorDTO.getPhone())
                    .append(" nu este formatat corect.")
                    .append(System.lineSeparator());
        }
        if(ValidationUtils.validateEmail(doctorDTO.getEmail())) {
            errors.append("Email-ul: ").append(doctorDTO.getEmail())
                    .append(" nu este formatat corect.")
                    .append(System.lineSeparator());
        }

        //lista doctorilor existenti
        List<DoctorDTO> existingDoctors = getDoctorsByFilters(null, null,null,null,null);

        //verificare daca datele introduse nu exista deja
        if(existingDoctors.stream().anyMatch(d -> d.getEmail().equalsIgnoreCase(doctorDTO.getEmail()))) {
            errors.append("Exista deja acest email: ").append(doctorDTO.getEmail())
                    .append(System.lineSeparator());
        }
        if(existingDoctors.stream().anyMatch(d -> d.getPhone().equalsIgnoreCase(doctorDTO.getPhone()))) {
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
        String emptyFieldsError = CheckFields.checkEmptyFields(
                DTOConverter.convertToMap(updates),
                Set.of("id_doctor"));
        if (emptyFieldsError != null) {
            errors.append(emptyFieldsError)
                    .append(System.lineSeparator());
        }

        //lista doctorilor existenti
        List<DoctorDTO> existingDoctors = getDoctorsByFilters(null, null,null,null,null);

        updates.forEach((field,value) ->{
            switch (field){
                case "first_name":
                    doctor.setFirst_name((String) value);
                    break;
                case "last_name":
                    doctor.setLast_name((String) value);
                    break;
                case "email":
                    if(ValidationUtils.validateEmail((String) value)) {
                        errors.append("Email-ul: ").append((String) value)
                                .append(" nu este formatat corect.")
                                .append(System.lineSeparator());
                    }
                    if(existingDoctors.stream().anyMatch(d -> d.getEmail().equalsIgnoreCase((String) value))) {
                        errors.append("Exista deja acest email: ").append(value)
                                .append(System.lineSeparator());
                    }
                    doctor.setEmail((String) value);
                    break;
                case "phone":
                    if(ValidationUtils.validatePhoneNumber((String) value)) {
                        errors.append("Numarul de telefon: ").append((String) value)
                                .append(" nu este formatat corect.")
                                .append(System.lineSeparator());
                    }
                    if(existingDoctors.stream().anyMatch(d -> d.getPhone().equalsIgnoreCase((String) value))) {
                        errors.append("Exista deja acest numar de telefon: ").append(value)
                                .append(System.lineSeparator());
                    }
                    doctor.setPhone((String) value);
                    break;
                case "id_clinic":
                    try {
                        Integer id_clinic = Integer.parseInt(value.toString());
                        Clinic clinic = clinicRepository.findClinicById(id_clinic).orElse(null);
                        if (clinic == null) {
                            errors.append(" Nu a fost gasita clinica cu id-ul: ").append(id_clinic)
                                    .append(System.lineSeparator());
                        }
                        doctor.setClinic(clinic);
                    } catch (NumberFormatException e) {
                        errors.append("ID-ul clinicii trebuie sa fie un numar valid.")
                                .append(System.lineSeparator());
                    }
                    break;
                case "id_specialization":
                    try {
                        Integer id_specialization = Integer.parseInt(value.toString());
                        Specialization specialization = specializationRepository.findSpecializationById(id_specialization).orElse(null);
                        if(specialization == null) {
                            errors.append("Nu a fost gasita specializarea cu id-ul: ").append(id_specialization)
                                    .append(System.lineSeparator());
                        }
                        doctor.setSpecialization(specialization);
                    } catch (NumberFormatException e) {
                        errors.append("ID-ul specializarii trebuie sa fie un numar valid.")
                                .append(System.lineSeparator());
                    }
                    break;
                case "id_doctor":
                case "created_at":
                case "updated_at":
                case "is_deleted":
                    errors.append("Acest camp nu poate fi modificat: ").append(field)
                            .append(System.lineSeparator());
                    break;
                default:
                    errors.append("Acest camp nu exista: ").append(field)
                            .append(System.lineSeparator());
                    break;
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
