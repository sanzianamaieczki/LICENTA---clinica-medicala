import { DoctorModel } from "./doctor.model";

export interface MedicalServicesModel{
    id_medical_service: number;
    medical_service_name: string;
    medical_service_type: MedicalServiceType;
    created_at: Date;
    updated_at: Date;
    is_deleted: boolean;
    price: number;
    // doctors: DoctorModel[];
}

export enum MedicalServiceType{
    Consultation = 'consultation',
    Analysis='analysis'
}