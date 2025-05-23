import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DoctorModel } from '../models/doctor.model';
import { MedicalServicesModel } from '../models/medical-services.model';
import { DoctorScheduleModel } from '../models/doctor-schedule.model';

@Injectable({
  providedIn: 'root'
})
export class DoctorService {

  private readonly apiUrl = 'http://localhost:8080/api/clinica-medicala/doctors';
    
    constructor(private readonly http: HttpClient) {}
  
    getDoctors(filters? : any): Observable<DoctorModel[]> {
      return this.http.get<any>(this.apiUrl, {params: filters});
    }
  
    getDoctorById(id: number): Observable<DoctorModel>{
      return this.http.get<any>(`${this.apiUrl}/${id}`);
    }

    getMedicalServices(): Observable<MedicalServicesModel[]>{
      return this.http.get<any>(`${this.apiUrl}/medical-services`);    
    }

    getDoctorMedicalServices(id: number): Observable<MedicalServicesModel[]>{
      return this.http.get<any>(`${this.apiUrl}/${id}/medical-services`);
    }
    
    getDoctorSchedule(id: number): Observable<DoctorScheduleModel[]>
    {
      return this.http.get<any>(`${this.apiUrl}/${id}/doctor-schedule`)
    } 

    addDoctor(doctor: any): Observable<any> {
      return this.http.post<any>(`${this.apiUrl}`, doctor);
    }
  
    updateDoctor(id: number, updates: any): Observable<any>{
      return this.http.patch<any>(`${this.apiUrl}/${id}`, updates);
    }
    
    deleteDoctor(id:number): Observable<any>{
      return this.http.delete<any>(`${this.apiUrl}/${id}`);
    }
  }   
  
  