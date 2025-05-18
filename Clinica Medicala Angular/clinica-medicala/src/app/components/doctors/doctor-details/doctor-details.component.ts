import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { DoctorModel } from '../../../models/doctor.model';
import { DoctorService } from '../../../services/doctor.service';
import { DayOfWeek, DoctorScheduleModel } from '../../../models/doctor-schedule.model';
import { DoctorMedicalServicesModel } from '../../../models/doctor-medical-services.model';
import { AppointmentModel } from '../../../models/appointment.model';
import { AppointmentService } from '../../../services/appointment.service';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';
import { CalendarOptions } from '@fullcalendar/core';
import { EventInput } from '@fullcalendar/core';

@Component({
  selector: 'app-doctor-details',
  templateUrl: './doctor-details.component.html',
  styleUrl: './doctor-details.component.css'
})
export class DoctorDetailsComponent implements OnInit {

  doctorId!: number;
  doctor?: DoctorModel;

  medicalServices: DoctorMedicalServicesModel[] = [];
  showSchedule = false;
  showServices = false;

  doctorSchedule: DoctorScheduleModel[] = [];
  appoinments: AppointmentModel[] = [];

  calendarOptions!: CalendarOptions;


  // weekDays:DayOfWeek[] = [
  //   DayOfWeek.Monday,
  //   DayOfWeek.Tuesday,
  //   DayOfWeek.Wednesday,
  //   DayOfWeek.Thursday,
  //   DayOfWeek.Friday,
  //   DayOfWeek.Saturday,
  //   DayOfWeek.Sunday
  // ];

  // dayMapping: {[key: string]: string} = {
  //   monday: 'Luni',
  //   tuesday: 'Marti',
  //   wednesday: 'Miercuri',
  //   thursday: 'Joi',
  //   friday: 'Vineri',
  //   saturday: 'Sambata',
  //   sunday: 'Duminica'
  // }
  // hours: number[] = Array.from({ length: 13 }, (_, i) => i + 8);


  constructor(private readonly route: ActivatedRoute,
    private readonly router: Router,
    private readonly doctorService: DoctorService,
    private readonly appointmentService: AppointmentService
  ) { }

  ngOnInit(): void {
    this.route.paramMap
      .subscribe((params: ParamMap) => {
        const id = Number(params.get('id'));
        this.doctorId = id;
        this.fetchDoctorDetails(id);
        
      });

      this.route.queryParams.subscribe((params) => {
        this.showSchedule = params['schedule'] === 'true';
        this.showServices = params['services'] === 'true';
      });
  }

  fetchDoctorDetails(doctorId: number) {
    this.doctorService.getDoctorById(doctorId)
    .subscribe({
      next: doctor => {
        this.doctor = doctor;
        this.doctorSchedule = this.doctor?.doctorSchedules || [];
        this.medicalServices = this.doctor?.medicalServices || [];
        this.appointmentService.getAppointmentsByDoctorId(this.doctorId)
        .subscribe({
          next: appointments => {
            this.appoinments = appointments;
            this.buildCalendar();
          }
        });
      },
      error: (err) => {
        console.error('Eroare la preluarea detaliilor doctorului:', err);
      }
    });
    
  }
  toogleSchedule() {
    this.router.navigate(['/doctors', this.doctorId], {
      queryParams: { schedule: !this.showSchedule },
    });
      
  }
  toogleServices() {
    this.router.navigate(['/doctors', this.doctorId], {
      queryParams: { services: !this.showServices },
    });
  }

  private buildCalendar(): void {
  this.calendarOptions = {
    plugins:      [ dayGridPlugin, timeGridPlugin ],
    initialView:  'dayGridMonth',
    headerToolbar:{
      left:  'prev,next today',
      center:'title',
      right: ''
    },
    height: 650,

    events: (fetchInfo, successCallback, failureCallback) => {
      try {
        const events: EventInput[] = [];
        const slotLen = 30 * 60_000;

        for (
          let d = new Date(fetchInfo.start);
          d < new Date(fetchInfo.end);
          d.setDate(d.getDate() + 1)
        ) {
          const dow = d
            .toLocaleDateString('en-US', { weekday: 'long' })
            .toLowerCase();

          const schedules = this.doctorSchedule.filter(
            sch => sch.day_of_week.toLowerCase() === dow
          );

          for (const sch of schedules) {
            const startTimeStr = sch.start_time instanceof Date
              ? sch.start_time.toTimeString().slice(0, 5)
              : sch.start_time;
            const endTimeStr = sch.end_time instanceof Date
              ? sch.end_time.toTimeString().slice(0, 5)
              : sch.end_time;
            const [sh, sm] = startTimeStr.split(':').map(n => +n);
            const [eh, em] = endTimeStr.split(':').map(n => +n);

            const startDate = new Date(d);
            startDate.setHours(sh, sm, 0, 0);
            const endDate = new Date(d);
            endDate.setHours(eh, em, 0, 0);

            for (
              let t = startDate.getTime();
              t + slotLen <= endDate.getTime();
              t += slotLen
            ) {
              const slotStart = new Date(t);
              if (!this.isOccupied(slotStart)) {
                const slotEnd = new Date(t + slotLen);
                events.push({
                  title: `${this.formatTime(slotStart)}–${this.formatTime(slotEnd)}`,
                  start: slotStart.toISOString(),
                  end:   slotEnd .toISOString(),
                  display: 'block',
                  color: 'crimson'
                });
              }
            }
          }
        }

        successCallback(events);
      } catch (err) {
        failureCallback(err as Error);
      }
    }
  };
}

  private isOccupied(slot: Date): boolean {
    return this.appoinments.some(appt =>
      new Date(appt.appointment_date).getTime() === slot.getTime()
    );
  }

  private formatTime(dt: Date): string {
    const hh = ('0' + dt.getHours()).slice(-2);
    const mm = ('0' + dt.getMinutes()).slice(-2);
    return `${hh}:${mm}`;
  }
}
