//package com.example.ClinicaMedicala.repository.DoctorRepositoryComponents;
//
//import com.example.ClinicaMedicala.entity.DoctorEntityComponents.DoctorMedicalServices;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//import java.util.List;
//import java.util.Optional;
//
//
//public interface DoctorMedicalServicesRepository extends JpaRepository<DoctorMedicalServices, Integer> {
//
//    @Query("SELECT dms FROM DoctorMedicalServices dms "+
//            "WHERE (:is_deleted IS NULL OR dms.is_deleted = :is_deleted) " +
//            "AND (:price IS NULL OR dms.price = :price)"
//    )
//    List<DoctorMedicalServices> findDoctorMedicalServicesByFilters(
//            @Param("is_deleted") Boolean is_deleted,
//            @Param("price") Double price
//    );
//
//    @Query("SELECT dms FROM DoctorMedicalServices dms WHERE dms.id_doctor_medical_service = :id_doctor_medical_service")
//    Optional<DoctorMedicalServices> findDoctorMedicalServicesById(int id_doctor_medical_service);
//
//    @Query("SELECT dms FROM DoctorMedicalServices dms WHERE dms.doctor.id_doctor = :id_doctor")
//    List<DoctorMedicalServices> findMedicalServicesByDoctorId(int id_doctor);
//
//    @Query("SELECT dms FROM DoctorMedicalServices dms WHERE dms.medicalServices.id_medical_service = :id_medical_service")
//    List<DoctorMedicalServices> findDoctorMedicalServicesByMedicalServiceId(int id_medical_service);
//}
