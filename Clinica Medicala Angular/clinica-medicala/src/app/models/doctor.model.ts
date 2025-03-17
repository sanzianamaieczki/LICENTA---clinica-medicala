import { DoctorScheduleModel } from "./doctor-schedule.model";
<<<<<<< HEAD
=======
import { MedicalServicesModel } from "./medical-services.model";
>>>>>>> ac6fd5c77964ebac69c618981232966f8798795d

export interface DoctorModel{
    id_doctor: number;
    last_name: string;
    first_name: string;
    email: string;
    phone: string;
    id_specialization: number;
<<<<<<< HEAD
    specialization_name: string;
    id_clinic: number;
    clinic_name: string;
    created_at: Date;
    updated_at: Date;
    is_deleted: boolean;
    schedule: DoctorScheduleModel[]
}
=======
    //specialization_name: string;
    id_clinic: number;
    //clinic_name: string;
    created_at: Date;
    updated_at: Date;
    is_deleted: boolean;

    doctorSchedules: DoctorScheduleModel[],
    medicalServices: MedicalServicesModel[];
}
>>>>>>> ac6fd5c77964ebac69c618981232966f8798795d
