import { DoctorMedicalServicesModel } from "./doctor-medical-services.model";
import { DoctorScheduleModel } from "./doctor-schedule.model";

export interface DoctorModel{
    id_doctor: number;
    last_name: string;
    first_name: string;
    email: string;
    phone: string;
    id_specialization: number;
    id_clinic: number;
    created_at: Date;
    updated_at: Date;
    is_deleted: boolean;

    doctorSchedules: DoctorScheduleModel[],
    medicalServices: DoctorMedicalServicesModel[];
}
