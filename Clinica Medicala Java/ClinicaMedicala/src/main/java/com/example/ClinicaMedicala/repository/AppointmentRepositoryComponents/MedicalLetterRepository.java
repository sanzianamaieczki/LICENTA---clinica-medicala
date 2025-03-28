package com.example.ClinicaMedicala.repository.AppointmentRepositoryComponents;

import com.example.ClinicaMedicala.entity.AppointmentEntityComponenents.MedicalLetter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MedicalLetterRepository extends JpaRepository<MedicalLetter, Integer> {
    @Query("SELECT ml FROM MedicalLetter ml WHERE ml.id_medical_letter = :id_medical_letter")
    Optional<MedicalLetter> findMedicalLetterById(int id_medical_letter);
}
