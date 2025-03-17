import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, retry } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ClinicService {
  private readonly apiUrl = 'http://localhost:8080/api/clinica-medicala/clinics';

  constructor(private readonly http: HttpClient) {}

  getClinics(filters? : any): Observable<any> {
    return this.http.get<any>(this.apiUrl, {params: filters});
  }

  getClinicById(id: number): Observable<any>{
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  getDoctorsByClinicId(id: number):Observable<any>{
    return this.http.get<any>(`${this.apiUrl}/${id}/doctors`);
  }

  addClinic(clinic: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}`, clinic);
  }

  updateClinic(id: number, updates: any): Observable<any>{
    return this.http.patch<any>(`${this.apiUrl}/${id}`, updates);
  }
  
  deleteClinic(id:number): Observable<any>{
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }
}
