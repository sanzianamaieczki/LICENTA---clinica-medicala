import { DoctorScheduleModel } from "./doctor-schedule.model";

export interface DoctorModel{
    id_doctor: number;
    last_name: string;
    first_name: string;
    email: string;
    phone: string;
    id_specialization: number;
    specialization_name: string;
    id_clinic: number;
    clinic_name: string;
    created_at: Date;
    updated_at: Date;
    is_deleted: boolean;
    schedule: DoctorScheduleModel[]
}