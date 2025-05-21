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
    plugins:[ dayGridPlugin, timeGridPlugin ],
    initialView: 'dayGridMonth',
    headerToolbar:{
      left:'prev next',
      center:'title',
      right: 'today'
    },
    eventTimeFormat: {
      hour: '2-digit',
      minute: '2-digit',
      meridiem: false,
      hour12: false
    },
    
    events: (info, successCallback, failureCallback) => {
      try {
        const events: any[] = [];
        const slotLen = 30 * 60000;

        for (let day = new Date(info.start); day < new Date(info.end); day.setDate(day.getDate() + 1)) {
          const dow = day.toLocaleDateString('en-US', { weekday: 'long' }).toLowerCase();

          const schedules = this.doctorSchedule.filter(
            schedule => schedule.day_of_week.toLowerCase() === dow
          );

          for (const schedule of schedules) {
            const startTime = schedule.start_time.slice(0, 5);
            const endTime = schedule.end_time.slice(0, 5);

            const [startHour, startMinute] = startTime.split(':').map(n => +n);
            const [endHour, endMinute] = endTime.split(':').map(n => +n);

            const startDate = new Date(day);
            startDate.setHours(startHour, startMinute, 0, 0);

            const endDate = new Date(day);
            endDate.setHours(endHour, endMinute, 0, 0);

            for (let time = startDate.getTime(); time + slotLen <= endDate.getTime(); time += slotLen) {
              const slotStart = new Date(time);
              if (!this.isOccupied(slotStart)) {
                const slotEnd = new Date(time + slotLen);
                events.push({
                  start: slotStart.toISOString(),
                  end: slotEnd .toISOString(),
                  display: 'block'
                  // color: 'crimson'
                });
              }
            }
          }
        }

        successCallback(events);
      } catch (err) {
        failureCallback(err as Error);
      }
    },
    eventClick: (info) => {
      this.addAppointment(info.event.start);
    }
  };
}

  private isOccupied(slot: Date): boolean {
    return this.appoinments.some(appoinment =>
      new Date(appoinment.appointment_date).getTime() === slot.getTime()
    );
  }

  addAppointment(date: Date | null) {
     if (date) {this.router.navigate(['/appointments', 'add-appointment']);}
  }
}
