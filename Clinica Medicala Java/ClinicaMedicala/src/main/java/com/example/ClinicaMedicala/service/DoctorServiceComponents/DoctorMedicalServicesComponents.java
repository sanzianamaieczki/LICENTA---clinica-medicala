package com.example.ClinicaMedicala.service.DoctorServiceComponents;

import com.example.ClinicaMedicala.dto.DoctorDTOComponents.DoctorMedicalServicesDTO;
import com.example.ClinicaMedicala.entity.DoctorEntityComponents.Doctor;
import com.example.ClinicaMedicala.entity.DoctorEntityComponents.DoctorMedicalServices;
import com.example.ClinicaMedicala.entity.DoctorEntityComponents.MedicalServices;
import com.example.ClinicaMedicala.repository.DoctorRepositoryComponents.DoctorMedicalServicesRepository;
import com.example.ClinicaMedicala.repository.DoctorRepositoryComponents.DoctorRepository;
import com.example.ClinicaMedicala.repository.DoctorRepositoryComponents.MedicalServicesRepository;
import com.example.ClinicaMedicala.utils.CheckFields;
import com.example.ClinicaMedicala.utils.DTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DoctorMedicalServicesComponents {
    @Autowired
    private MedicalServicesRepository medicalServicesRepository;

    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private DoctorMedicalServicesRepository doctorMedicalServicesRepository;

    public DoctorMedicalServicesDTO addDoctorMedicalService(DoctorMedicalServicesDTO doctorMedicalServicesDTO) {
        StringBuilder errors = new StringBuilder();

        String emptyFieldsError = CheckFields.checkEmptyFields(
                DTOConverter.convertToMap(doctorMedicalServicesDTO),
                Set.of("id_doctor_medical_service", "medicalService", "appointments"));
        if (emptyFieldsError != null) {
            errors.append(emptyFieldsError)
                    .append(System.lineSeparator());
        }

        Doctor doctor = doctorRepository.findDoctorById(doctorMedicalServicesDTO.getId_doctor()).orElse(null);
        if(doctor == null) {
            errors.append("Doctorul cu id-ul: ").append(doctorMedicalServicesDTO.getId_doctor()).append(" nu exista.")
                    .append(System.lineSeparator());
        }else {
            boolean existingService = doctor.getDoctorMedicalServices().stream()
                    .filter(doctorMedicalServices -> !doctorMedicalServices.getIs_deleted())
                    .anyMatch(doctorMedicalServices -> doctorMedicalServices.getMedicalService().getMedical_service_name().equalsIgnoreCase(doctorMedicalServicesDTO.getMedicalService().getMedical_service_name())
                            && doctorMedicalServices.getPrice().equals(doctorMedicalServicesDTO.getPrice()));

            if (existingService) {
                errors.append("Exista deja acest serviciu medical: ")
                        .append(doctorMedicalServicesDTO.getId_medical_service())
                        .append(" pentru acest Doctor: ")
                        .append(doctorMedicalServicesDTO.getId_doctor())
                        .append(System.lineSeparator());
            }
        }

        MedicalServices medicalService = medicalServicesRepository.findById(doctorMedicalServicesDTO.getId_medical_service()).orElse(null);

        if(!errors.isEmpty()) {
            throw new IllegalArgumentException(errors.toString().trim());
        }
        if(medicalService == null) {
            errors.append("Serviciul medical cu id-ul: ").append(doctorMedicalServicesDTO.getId_doctor_medical_service()).append(" nu exista.")
                    .append(System.lineSeparator());
        }

        DoctorMedicalServices doctorMedicalServices = new DoctorMedicalServices(doctorMedicalServicesDTO);
        doctorMedicalServices.setDoctor(doctor);
        doctorMedicalServices.setMedicalService(medicalService);
        doctorMedicalServices.setCreated_at(new Date());
        doctorMedicalServices.setUpdated_at(null);
        doctorMedicalServices.setIs_deleted(false);
        if(doctor!=null) {
            doctor.getDoctorMedicalServices().add(doctorMedicalServices);
            doctorRepository.save(doctor);
        }

        return new DoctorMedicalServicesDTO(doctorMedicalServices);
    }

    public DoctorMedicalServicesDTO updateDoctorMedicalService(Integer id_doctor_medical_service, Integer id_doctor, Map<String, Object> updates) {
        //verificari necesare
        StringBuilder errors = new StringBuilder();

        //verificare daca exista serviciul medical mentionat - daca nu exista, nu mai continuam verificarile
        DoctorMedicalServices doctorMedicalServices = doctorMedicalServicesRepository.findDoctorMedicalServicesById(id_doctor_medical_service)
                .orElseThrow(() -> new IllegalArgumentException("Nu a fost gasit serviciul medical cu id-ul: " + id_doctor_medical_service));

        if(!doctorMedicalServices.getDoctor().getId_doctor().equals(id_doctor)){
            throw new IllegalArgumentException("Serviciul medical cu id-ul: " + id_doctor_medical_service + " nu apartine doctorului: " + id_doctor);
        }

        String emptyFieldsError = CheckFields.checkEmptyFields(
                DTOConverter.convertToMap(updates),
                Set.of("id_doctor_medical_service", "doctors")
        );
        if (emptyFieldsError != null) {
            errors.append(emptyFieldsError).append(System.lineSeparator());
        }

        updates.forEach((field,value) ->{
            switch (field){
                case "id_medical_service":
                    try {
                        Integer newIdMedicalService = Integer.parseInt((String) value);
                        if(!newIdMedicalService.equals(doctorMedicalServices.getId_doctor_medical_service())) {
                            MedicalServices newMedicalService = medicalServicesRepository.findMedicalServicesById(newIdMedicalService).orElse(null);
                            if(newMedicalService!=null) {
                                doctorMedicalServices.setMedicalService(newMedicalService);
                            }
                            else{
                                errors.append("Nu a fost gasit serviciul medical cu id-ul: ").append(newIdMedicalService);
                            }

                        }
                    }catch (Exception e) {
                        errors.append("Id-ul serviciului medical nu este valid.").append(System.lineSeparator());
                    }
                    break;
                case "price":
                    try {
                        Double newPrice = Double.parseDouble((String) value);
                        doctorMedicalServices.setPrice(newPrice);
                    }
                    catch (Exception e) {
                        errors.append("Pretul nu este formatat corect").append(System.lineSeparator());
                    }
                    break;
                case "id_doctor":
                case "id_doctor_medical_service":
                case "is_deleted":
                    errors.append("Acest camp nu poate fi modificat: ").append(field).append(System.lineSeparator());
                    break;
                default:
                    errors.append("Acest camp nu exista: ").append(field).append(System.lineSeparator());
                    break;
            }
        });

        Doctor doctor = doctorMedicalServices.getDoctor();
        boolean existingDuplicates = doctor.getDoctorMedicalServices().stream()
                .filter(dms -> !dms.getIs_deleted() && !dms.getId_doctor_medical_service().equals(id_doctor_medical_service))
                .anyMatch(dms -> dms.getMedicalService().getMedical_service_name().equalsIgnoreCase(doctorMedicalServices.getMedicalService().getMedical_service_name())
                        && !dms.getPrice().equals(doctorMedicalServices.getPrice()));

        if(existingDuplicates) {
            errors.append("Doctorul cu id-ul: ").append(id_doctor)
                    .append(" are deja acest serviciu: ").append(doctorMedicalServices.getMedicalService().getMedical_service_name())
                    .append(" cu pretul: ").append(doctorMedicalServices.getPrice())
                    .append(System.lineSeparator());
        }

        //afisarea erorilor
        if(!errors.isEmpty()) {
            throw new IllegalArgumentException(errors.toString().trim());
        }

        DoctorMedicalServices savedDoctorMedicalService = doctorMedicalServicesRepository.save(doctorMedicalServices);
        return new DoctorMedicalServicesDTO(savedDoctorMedicalService);
    }

    public void deleteDoctorMedicalService(Integer id_doctor_medical_service, Integer id_doctor) {
        DoctorMedicalServices doctorMedicalServices = doctorMedicalServicesRepository.findDoctorMedicalServicesById(id_doctor_medical_service)
                .orElseThrow(() -> new IllegalArgumentException("Nu a fost gasit pentru acest doctor serviciul medical cu id-ul: " + id_doctor_medical_service));

        if(!doctorMedicalServices.getDoctor().getId_doctor().equals(id_doctor)) {
            throw new IllegalArgumentException("Serviciul medical nu apartine doctorului cu id: " + id_doctor);
        }
        if(doctorMedicalServices.getIs_deleted()){
            throw new IllegalArgumentException("Acest serviciu medical al doctorului a fost deja sters");
        }

        doctorMedicalServices.setIs_deleted(true);
        doctorMedicalServicesRepository.save(doctorMedicalServices);
    }
}
