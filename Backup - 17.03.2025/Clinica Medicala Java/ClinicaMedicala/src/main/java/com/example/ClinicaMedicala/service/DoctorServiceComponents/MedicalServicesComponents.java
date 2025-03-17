//package com.example.ClinicaMedicala.service.DoctorServiceComponents;
//
//import com.example.ClinicaMedicala.dto.DoctorDTOComponents.MedicalServicesDTO;
//import com.example.ClinicaMedicala.entity.DoctorEntityComponents.Doctor;
//import com.example.ClinicaMedicala.enums.DoctorEnumComponents.MedicalServicesType;
//import com.example.ClinicaMedicala.repository.DoctorRepositoryComponents.DoctorRepository;
//import com.example.ClinicaMedicala.repository.DoctorRepositoryComponents.MedicalServicesRepository;
//import com.example.ClinicaMedicala.utils.CheckFields;
//import com.example.ClinicaMedicala.utils.DTOConverter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.*;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//@Service
//public class MedicalServicesComponents {
//    @Autowired
//    private MedicalServicesRepository medicalServicesRepository;
//
//    @Autowired
//    private DoctorRepository doctorRepository;
//
//    public List<MedicalServicesDTO> getMedicalServicesByFilters(
//            Boolean is_deleted,
//            String medical_service_name,
//            String medical_service_type
//    ){
//        return medicalServicesRepository.findMedicalServicesByFilters(is_deleted, medical_service_name , medical_service_type).stream()
//                .map(MedicalServicesDTO::new)
//                .collect(Collectors.toList());
//    }
//
//    public Optional<MedicalServicesDTO> getMedicalServicesById(int id_medical_service) {
//        return medicalServicesRepository.findMedicalServicesById(id_medical_service)
//                .map(MedicalServicesDTO::new);
//    }
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
//                        ms.getMedical_service_type().equalsIgnoreCase(String.valueOf(medicalServices.getMedical_service_type())) && !ms.getIs_deleted()
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
