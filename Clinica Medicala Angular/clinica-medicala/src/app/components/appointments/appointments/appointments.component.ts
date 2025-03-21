import { Component, OnInit } from '@angular/core';
import { AppointmentModel } from '../../../models/appointment.model';
import { AppointmentService } from '../../../services/appointment.service';

@Component({
  selector: 'app-appointments',
  templateUrl: './appointments.component.html',
  styleUrl: './appointments.component.css'
})
export class AppointmentsComponent implements OnInit {
  appointments: AppointmentModel[] = []; 
  
  constructor(
    private readonly appointmentService: AppointmentService,
  ) {}

  ngOnInit(): void {
    this.fetchAppointments();
  }

  fetchAppointments(){
    this.appointmentService.getAppointments().subscribe({
      next: (data) => {
        this.appointments = data
      },
      error: (err) => {
        console.log('Eroare la preluarea programarilor', err);
      }
    })
  }
}
