import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, retry } from 'rxjs';
import { PatientModel } from '../models/patient.model';

@Injectable({
  providedIn: 'root',
})
export class PatientsService {
  private readonly apiUrl = 'http://localhost:8080/api/clinica-medicala/patients';

  constructor(private readonly http: HttpClient) {}

  getPatients(filters? : any): Observable<PatientModel[]> {
    return this.http.get<any>(this.apiUrl, {params: filters});
  }

  getPatientById(id: number): Observable<PatientModel>{
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  addPatient(patient: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}`, patient);
  }

  updatePatient(id: number, updates: any): Observable<any>{
    return this.http.patch<any>(`${this.apiUrl}/${id}`, updates);
  }
  
  deletePatient(id:number): Observable<any>{
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }
}
