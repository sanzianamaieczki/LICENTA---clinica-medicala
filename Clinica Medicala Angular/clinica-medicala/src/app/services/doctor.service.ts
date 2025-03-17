import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DoctorService {

  private readonly apiUrl = 'http://localhost:8080/api/clinica-medicala/doctors';
    
    constructor(private readonly http: HttpClient) {}
  
    getDoctors(filters? : any): Observable<any> {
      return this.http.get<any>(this.apiUrl, {params: filters});
    }
  
    getDoctorById(id: number): Observable<any>{
      return this.http.get<any>(`${this.apiUrl}/${id}`);
    }
<<<<<<< HEAD
=======

    getMedicalServices(): Observable<any>{
      return this.http.get<any>(`${this.apiUrl}/medical-services`);    
    }

    getDoctorMedicalServices(id: number): Observable<any>{
      return this.http.get<any>(`${this.apiUrl}/${id}/medical-services`);
    }
>>>>>>> ac6fd5c77964ebac69c618981232966f8798795d
    
    getDoctorSchedule(id: number): Observable<any>
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
  
  