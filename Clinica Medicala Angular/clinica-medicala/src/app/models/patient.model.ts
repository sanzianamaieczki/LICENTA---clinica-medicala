import { AppointmentModel } from "./appointment.model";

export interface PatientModel{
    
    id_patient: number;
    last_name: string;
    first_name: string;
    email: string;
    phone: string;
    national_id: string;
    birth_date: Date;
    created_at: Date;
    updated_at: Date;
    is_deleted: boolean;

    appointments: AppointmentModel[];
}
