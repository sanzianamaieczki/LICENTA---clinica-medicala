export interface MedicalLetterModel{
    id_medical_letter: number;
    id_appointment: number;
    diagnosis: string;
    symptoms: string;
    content: string;
    created_at: Date;
    updated_at: Date;
    is_deleted: boolean;
}
