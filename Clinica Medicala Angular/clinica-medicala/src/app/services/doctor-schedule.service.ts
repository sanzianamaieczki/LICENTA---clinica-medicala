// import { HttpClient } from '@angular/common/http';
// import { Injectable } from '@angular/core';
// import { Observable } from 'rxjs';

// @Injectable({
//   providedIn: 'root'
// })
// export class DoctorScheduleService {

//   private readonly apiUrl = 'http://localhost:8080/api/clinica-medicala/doctors/doctor-schedule';
    
//     constructor(private readonly http: HttpClient) {}
  
//     getDoctorSchedule(filters? : any): Observable<any> {
//       return this.http.get<any>(this.apiUrl, {params: filters});
//     }
  
//     getDoctorScheduleById(id: number): Observable<any>{
//       return this.http.get<any>(`${this.apiUrl}/${id}`);
//     }
  
//     addDoctorSchedule(doctor_schedule: any): Observable<any> {
//       return this.http.post<any>(`${this.apiUrl}`, doctor_schedule);
//     }
  
//     updateDoctorSchedule(id: number, updates: any): Observable<any>{
//       return this.http.patch<any>(`${this.apiUrl}/${id}`, updates);
//     }
    
//     deleteDoctorSchedule(id:number): Observable<any>{
//       return this.http.delete<any>(`${this.apiUrl}/${id}`);
//     }
//   }   
  
  