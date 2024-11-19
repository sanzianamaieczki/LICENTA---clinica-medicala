import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ClinicService {
  private readonly apiUrl = 'http://localhost:8080/api/clinica-medicala/clinics';

  constructor(private readonly http: HttpClient) {}

  getClinics(): Observable<any> {
    return this.http.get<any>(this.apiUrl);
  }
}
