import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SpecializationService {

  private readonly apiUrl = 'http://localhost:8080/api/clinica-medicala/specializations';
  
  constructor(private readonly http: HttpClient) {}

  getSpecializations(filters? : any): Observable<any> {
    return this.http.get<any>(this.apiUrl, {params: filters});
  }

  getSpecializationById(id: number): Observable<any>{
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  addSpecialization(specialization: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}`, specialization);
  }

  updateSpecialization(id: number, updates: any): Observable<any>{
    return this.http.patch<any>(`${this.apiUrl}/${id}`, updates);
  }
  
  deleteSpecialization(id:number): Observable<any>{
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }
}   

