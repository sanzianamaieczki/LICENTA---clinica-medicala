import { DoctorModel } from "./doctor.model";

export interface ClinicModel{
    id_clinic: number;
    clinic_name: string;
    clinic_phone: string;
    clinic_address: string;
    created_at: Date;
    updated_at: Date;
    is_deleted: boolean;
    doctors: DoctorModel[];
}