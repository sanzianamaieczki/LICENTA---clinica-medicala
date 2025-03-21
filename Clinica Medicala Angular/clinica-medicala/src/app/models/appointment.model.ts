import { DoctorModel } from "./doctor.model";

export interface AppointmentModel{
    id_appointment: number;
    id_patient: number;
    id_medical_service: number;
    appointment_date: Date;
    appointment_status: AppointmentStatus;
    created_at: Date;
    updated_at: Date;
    is_deleted: boolean;
    doctors: DoctorModel[];
}

export enum AppointmentStatus{
    Created = 'created',
    Compleates='compleated',
    Canceled='canceled'
}