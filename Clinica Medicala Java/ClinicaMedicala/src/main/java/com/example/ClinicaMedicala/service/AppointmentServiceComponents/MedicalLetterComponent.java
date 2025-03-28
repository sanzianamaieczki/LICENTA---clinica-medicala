package com.example.ClinicaMedicala.service.AppointmentServiceComponents;
import com.example.ClinicaMedicala.dto.AppointmentDTOComponents.MedicalLetterDTO;
import com.example.ClinicaMedicala.entity.AppointmentEntityComponenents.Appointment;
import com.example.ClinicaMedicala.entity.AppointmentEntityComponenents.MedicalLetter;
import com.example.ClinicaMedicala.enums.AppointmentStatus;
import com.example.ClinicaMedicala.repository.AppointmentRepositoryComponents.AppointmentRepository;
import com.example.ClinicaMedicala.repository.AppointmentRepositoryComponents.MedicalLetterRepository;
import com.example.ClinicaMedicala.utils.CheckFields;
import com.example.ClinicaMedicala.utils.DTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.Set;

@Service
public class MedicalLetterComponent {
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private MedicalLetterRepository medicalLetterRepository;

    public MedicalLetterDTO addMedicalLetter(MedicalLetterDTO medicalLetterDTO) {
        //verificari necesare
        StringBuilder errors = new StringBuilder();

        //verificare daca datele introduse sunt nule
        String emptyFieldsError = CheckFields.checkEmptyFields(
                DTOConverter.convertToMap(medicalLetterDTO),
                Set.of("id_medical_letter"));
        if (emptyFieldsError != null) {
            errors.append(emptyFieldsError)
                    .append(System.lineSeparator());
        }

        Appointment appointment = appointmentRepository.findAppointmentById(medicalLetterDTO.getId_appointment())
                .orElseThrow(()-> new IllegalArgumentException("Nu exista o programare cu id-ul: " + medicalLetterDTO.getId_appointment()));

        if(appointment.getMedicalLetter() != null && !appointment.getMedicalLetter().getIs_deleted()){
            errors.append("Exista deja o scrisoare medicala pentru aceasta programare.");
        }

        if(!errors.isEmpty()) {
            throw new IllegalArgumentException(errors.toString().trim());
        }

        MedicalLetter medicalLetter = new MedicalLetter(medicalLetterDTO);
        medicalLetter.setAppointment(appointment);
        medicalLetter.setCreated_at(new Date());
        medicalLetter.setUpdated_at(null);
        medicalLetter.setIs_deleted(false);

        MedicalLetter savedMedicalLetter = medicalLetterRepository.save(medicalLetter);

        appointment.setAppointment_status(AppointmentStatus.completed);
        appointment.setMedicalLetter(savedMedicalLetter);
        appointmentRepository.save(appointment);

        return new MedicalLetterDTO(savedMedicalLetter);
    }

    public MedicalLetterDTO updateMedicalLetter(Integer id_medical_letter, Integer id_appointment, Map<String,Object> updates) {
        //verificari necesare
        StringBuilder errors = new StringBuilder();

        MedicalLetter medicalLetter = medicalLetterRepository.findMedicalLetterById(id_medical_letter)
                .orElseThrow(()-> new IllegalArgumentException("Nu exista scrisoarea medicala cu id-ul: " + id_medical_letter));

        if(!medicalLetter.getAppointment().getId_appointment().equals(id_appointment)) {
            throw new IllegalArgumentException("Scrisoarea medicala cu id-ul "+id_medical_letter+" nu apartine programarii cu id-ul: "+id_appointment);
        }

        //verificare daca datele introduse sunt nule
        String emptyFieldsError = CheckFields.checkEmptyFields(
                DTOConverter.convertToMap(updates),
                Set.of("id_medical_letter", "id_appointment"));
        if (emptyFieldsError != null) {
            errors.append(emptyFieldsError)
                    .append(System.lineSeparator());
        }

        updates.forEach((field, value)->{
            switch (field){
                case "diagnosis":
                    medicalLetter.setDiagnosis((String) value);
                    break;
                case "symptoms":
                    medicalLetter.setSymptoms((String) value);
                    break;
                case "content":
                    medicalLetter.setContent((String) value);
                    break;
                case "id_medical_letter":
                case "id_appointment":
                case "created_at":
                case "updated_at":
                case "is_deleted":
                    errors.append("Acest camp nu poate fi modificat: ").append(field);
                    break;
                default:
                    errors.append("Acest camp nu exista: ").append(field);
                    break;
            }
        });

        if(!errors.isEmpty()) {
            throw new IllegalArgumentException(errors.toString().trim());
        }

        medicalLetter.setUpdated_at(new Date());

        MedicalLetter savedMedicalLetter = medicalLetterRepository.save(medicalLetter);

        return new MedicalLetterDTO(savedMedicalLetter);
    }

    public void deleteMedicalLetter(Integer id_medical_letter, Integer id_appointment) {
        MedicalLetter medicalLetter = medicalLetterRepository.findMedicalLetterById(id_medical_letter)
                .orElseThrow(() -> new IllegalArgumentException("Nu a fost gasit pentru aceasta programare, scrisoarea medicala cu id-ul: " + id_medical_letter));

        if(!medicalLetter.getAppointment().getId_appointment().equals(id_appointment)) {
            throw new IllegalArgumentException("Scrisoarea medicala nu apartine programarii: " + id_appointment);
        }
        if(medicalLetter.getIs_deleted()){
            throw new IllegalArgumentException("Acesta scrisoare medicala a fost deja stearsa");
        }

        Appointment appointment = appointmentRepository.findAppointmentById(medicalLetter.getAppointment().getId_appointment())
                .orElseThrow(()-> new IllegalArgumentException("Nu exista o programare cu id-ul: " + medicalLetter.getAppointment().getId_appointment()));

        appointment.setAppointment_status(AppointmentStatus.created);
        appointmentRepository.save(appointment);

        medicalLetter.setIs_deleted(true);
        medicalLetterRepository.save(medicalLetter);
    }
}
