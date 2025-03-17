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
  
  