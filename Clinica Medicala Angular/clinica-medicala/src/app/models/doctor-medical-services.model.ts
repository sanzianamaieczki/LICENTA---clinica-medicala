import { DoctorModel } from "./doctor.model";
import { MedicalServicesModel } from "./medical-services.model";

export interface DoctorMedicalServicesModel{
    id_doctor_medical_service: number;
    id_doctor: number;
    id_medical_service: number;
    price: number;
    created_at: Date;
    updated_at: Date;
    is_deleted: boolean;

    medicalService: MedicalServicesModel;
    // doctors: DoctorModel[];
}
