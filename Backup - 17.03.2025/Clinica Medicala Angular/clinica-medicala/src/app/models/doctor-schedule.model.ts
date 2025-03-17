export interface DoctorScheduleModel{
    id_doctor: number,
    day_of_week: DayOfWeek,
    start_time: Date,
    end_time: Date,
    is_deleted: boolean
}

export enum DayOfWeek{
    Monday = 'monday',
    Tuesday = 'tuesday',
    Wednesday = 'wednesday',
    Thursday = 'thursday',
    Friday = 'friday',
    Saturday = 'saturday',
    Sunday = 'sunday'
}