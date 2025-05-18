import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AppointmentModel } from '../models/appointment.model';

@Injectable({
  providedIn: 'root',
})
export class AppointmentService {
  private readonly apiUrl = 'http://localhost:8080/api/clinica-medicala/appointments';

  constructor(private readonly http: HttpClient) {}

  getAppointments(filters? : any): Observable<AppointmentModel[]> {
    return this.http.get<any>(this.apiUrl, {params: filters});
  }

  getAppointmentById(id: number): Observable<AppointmentModel>{
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  getAppointmentsByDoctorId(id: number): Observable<AppointmentModel[]>{
    return this.http.get<any>(`${this.apiUrl}/doctor/${id}`);
  }

}
