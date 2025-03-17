import { DoctorModel } from "./doctor.model";

export interface SpecializationModel{
    id_specialization: number;
    specialization_name: string;
    created_at: Date;
    updated_at: Date;
    is_deleted: boolean;
    doctors: DoctorModel[];
}