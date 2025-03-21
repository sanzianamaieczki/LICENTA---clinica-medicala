package com.example.ClinicaMedicala.service;

import com.example.ClinicaMedicala.dto.AppointmentDTOComponents.AppointmentDTO;
import com.example.ClinicaMedicala.repository.AppointmentRepositoryComponents.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppointmentService {
    @Autowired
    AppointmentRepository appointmentRepository;

    public List<AppointmentDTO> getAppointmentsByFilters(
            Boolean is_deleted,
            String appointment_status,
            Date appointment_date
    ){
        return appointmentRepository.findAppointmentsByFilters(is_deleted, appointment_status, appointment_date).stream()
                .map(AppointmentDTO::new)
                .collect(Collectors.toList());
    }
    public Optional<AppointmentDTO> getAppointmentById(int id_appointment) {
        return appointmentRepository.findAppointmentById(id_appointment)
                .map(AppointmentDTO::new);
    }


}

////    public List<MedicalServicesDTO> getMedicalServicesByDoctor(int id_doctor) {
////        return medicalServicesRepository.findMedicalServicesByDoctorId(id_doctor).stream()
////                .map(MedicalServicesDTO::new)
////                .collect(Collectors.toList());
////    }
//
//    public MedicalServicesDTO addMedicalService(MedicalServicesDTO medicalServicesDTO) {
//
//        //verificari necesare
//        StringBuilder errors = new StringBuilder();
//
//        //verificare daca datele introduse sunt nule
//        String emptyFieldsError = CheckFields.checkEmptyFields(
//                DTOConverter.convertToMap(medicalServicesDTO),
//                Set.of("id_medical_service"));
//        if (emptyFieldsError != null) {
//            errors.append(emptyFieldsError)
//                    .append(System.lineSeparator());
//        }
//
//        //lista serviciilor medicale existente
//        List<MedicalServicesDTO> existingMedicalServices = getMedicalServicesByFilters(null, null,null);
//
//        //verificare daca datele introduse nu exista deja (daca are acelasi nume, acelasi tip si acelasi medic asignat)
//        if(existingMedicalServices.stream().anyMatch(ms ->
//                ms.getMedical_service_name().equalsIgnoreCase(medicalServicesDTO.getMedical_service_name()) &&
//                        ms.getMedical_service_type().equalsIgnoreCase(medicalServicesDTO.getMedical_service_type()) && !ms.getIs_deleted()
//        )) {
//            errors.append("Exista deja acest serviciu medical: ").append(medicalServicesDTO.getMedical_service_name())
//                    .append(", de acest tip: ").append(medicalServicesDTO.getMedical_service_type())
//                    .append(System.lineSeparator());
//        }
//
//        //verificare daca tipul serviciului medical este corect
//        if (CheckFields.isValidEnumValue(
//                Stream.of(MedicalServicesType.values()).map(Enum::name).toList(),
//                medicalServicesDTO.getMedical_service_type())) {
//            errors.append("Tipul serviciului medical: ")
//                    .append(medicalServicesDTO.getMedical_service_type())
//                    .append(" este invalid")
//                    .append(System.lineSeparator());
//        }
////
////        //verificare daca introducem o durata mai mica decat 0
////        if(medicalServicesDTO.getDuration()!= null && medicalServicesDTO.getDuration() < 1){
////            errors.append("Durata unui serviciu medical trebuie sa aiba mai mult de 1 minut")
////                    .append(System.lineSeparator());
////        }
//
//        //afisarea erorilor
//        if(!errors.isEmpty()) {
//            throw new IllegalArgumentException(errors.toString().trim());
//        }
//
//        MedicalServices medicalService = new MedicalServices(medicalServicesDTO);
//        medicalService.setCreated_at(new Date());
//        medicalService.setUpdated_at(null);
//        medicalService.setIs_deleted(false);
//
//        MedicalServices savedMedicalService = medicalServicesRepository.save(medicalService);
//        return new MedicalServicesDTO(savedMedicalService);
//    }
//
//    public MedicalServicesDTO updateMedicalService(Integer id_medical_service, Map<String, Object> updates) {
//
//        //verificari necesare
//        StringBuilder errors = new StringBuilder();
//
//        //verificare daca exista serviciul medical mentionat - daca nu exista, nu mai continuam verificarile
//        MedicalServices medicalServices = medicalServicesRepository.findMedicalServicesById(id_medical_service)
//                .orElseThrow(() -> new IllegalArgumentException("Nu a fost gasit serviciul medical cu id-ul: " + id_medical_service));
//
//        //verificare daca nu se introduc date nule
//        String emptyFieldsError = CheckFields.checkEmptyFields(
//                DTOConverter.convertToMap(updates),
//                Set.of("id_medical_service"));
//        if (emptyFieldsError != null) {
//            errors.append(emptyFieldsError)
//                    .append(System.lineSeparator());
//        }
//
//        //lista serviciilor medicale existenti
//        List<MedicalServicesDTO> existingMedicalServices = getMedicalServicesByFilters(null,null,null);
//
//        updates.forEach((field,value) ->{
//            switch (field){
//                case "medical_service_name":
//                    medicalServices.setMedical_service_name((String) value);
//                    break;
//                case "medical_service_type":
//                    if (CheckFields.isValidEnumValue(
//                            Stream.of(MedicalServicesType.values()).map(Enum::name).toList(),
//                            (String) value)) {
//                        errors.append("Tipul serviciului medical: ")
//                                .append(value)
//                                .append(" este invalid")
//                                .append(System.lineSeparator());
//                    }
//                    medicalServices.setMedical_service_type(MedicalServicesType.valueOf((String) value));
//                    break;
////                case "id_doctor":
////                    try {
////                        Integer id_doctor = Integer.parseInt(value.toString());
////                        Doctor doctor = doctorRepository.findDoctorById(id_doctor).orElse(null);
////                        if (doctor == null) {
////                            errors.append("Nu a fost gasit doctorul cu id-ul: ").append(id_doctor)
////                                    .append(System.lineSeparator());
////                        }
////                        medicalServices.setDoctor(doctor);
////                    } catch (NumberFormatException e) {
////                        errors.append("ID-ul doctorului trebuie sa fie un numar valid.")
////                                .append(System.lineSeparator());
////                    }
////                    break;
//                case "id_medical_service":
//                case "created_at":
//                case "updated_at":
//                case "is_deleted":
//                    errors.append("Acest camp nu poate fi modificat: ").append(field)
//                            .append(System.lineSeparator());
//                    break;
//                default:
//                    errors.append("Acest camp nu exista: ").append(field)
//                            .append(System.lineSeparator());
//                    break;
//            }
//        });
//
//        //verificare daca datele introduse nu exista deja (daca are acelasi nume, acelasi tip si acelasi medic asignat)
//        if(existingMedicalServices.stream().anyMatch(ms ->
//                ms.getMedical_service_name().equalsIgnoreCase(medicalServices.getMedical_service_name()) &&
//                        ms.getMedical_service_type().equalsIgnoreCase(String.valueOf(medicalServices.getMedical_service_type())) &&
//                        !ms.getIs_deleted()
//        )) {
//            errors.append("Exista deja acest serviciu medical: ").append(medicalServices.getMedical_service_name())
//                    .append(", de acest tip: ").append(medicalServices.getMedical_service_type())
//                    .append(System.lineSeparator());
//        }
//
//        //afisarea erorilor
//        if(!errors.isEmpty()) {
//            throw new IllegalArgumentException(errors.toString().trim());
//        }
//
//        medicalServices.setUpdated_at(new Date());
//        MedicalServices savedMedicalService = medicalServicesRepository.save(medicalServices);
//        return new MedicalServicesDTO(savedMedicalService);
//    }
//
//    public void deleteMedicalService(Integer id_medical_service) {
//        MedicalServices medicalServices = medicalServicesRepository.findMedicalServicesById(id_medical_service)
//                .orElseThrow(() -> new IllegalArgumentException("Nu a fost gasit serviciul medical cu id-ul: " + id_medical_service));
//
//        if(medicalServices.getIs_deleted()){
//            throw  new IllegalArgumentException("Acest serviciu medical a fost deja sters: " + id_medical_service);
//        }
//
//        medicalServices.setIs_deleted(true);
//        medicalServices.setUpdated_at(new Date());
//        medicalServicesRepository.save(medicalServices);
//    }
//}
