import { Component, OnInit } from '@angular/core';
import { ClinicService } from '../../services/clinic.service';
import { ClinicModel } from '../../models/clinic.model';

@Component({
  selector: 'app-clinics',
  templateUrl: './clinics.component.html',
  styleUrls: ['./clinics.component.css'],
})
export class ClinicsComponent implements OnInit {
  clinics: any[] = [];

  constructor(private readonly clinicService: ClinicService) {}

  ngOnInit(): void {
    this.fetchClinics();
    console.log(this.clinics);
  }

  fetchClinics() {
    this.clinicService.getClinics().subscribe({
      next: (data) => {
        this.clinics = data;

        this.clinics.forEach((clinic)=>{
          this.fetchDoctors(clinic)
        })
      },
      error: (err) => {
        console.error('Eroare la preluarea clinicilor', err);
      },
    });
  }

  fetchDoctors(clinic:ClinicModel){
    this.clinicService.getDoctorsByClinicId(clinic.id_clinic).subscribe({
      next: (doctors)=>{
        this.clinics = this.clinics.map(c=>{
          if(c.id_clinic === clinic.id_clinic){
            return {...c, doctors: doctors}
          }
          else{
            return c
          }
        })
      },
      error: (errors) =>{
        console.error(`Eroare la preluarea doctorilor de la clinica: ${clinic.clinic_name}`, errors)
        this.clinics = this.clinics.map(c=>{
          if(c.id_clinic === clinic.id_clinic){
            return {...c,doctors:[]}
          }
          else{
            return c;
          }
        })
      }
    })
  }
}
