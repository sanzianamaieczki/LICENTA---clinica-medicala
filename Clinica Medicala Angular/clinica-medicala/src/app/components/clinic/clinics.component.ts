import { Component, OnInit } from '@angular/core';
import { ClinicService } from '../../services/clinic.service';
<<<<<<< HEAD
=======
import { ClinicModel } from '../../models/clinic.model';
>>>>>>> ac6fd5c77964ebac69c618981232966f8798795d

@Component({
  selector: 'app-clinics',
  templateUrl: './clinics.component.html',
  styleUrls: ['./clinics.component.css'],
})
export class ClinicsComponent implements OnInit {
  clinics: any[] = [];

<<<<<<< HEAD
  constructor(private clinicService: ClinicService) {}
=======
  constructor(private readonly clinicService: ClinicService) {}
>>>>>>> ac6fd5c77964ebac69c618981232966f8798795d

  ngOnInit(): void {
    this.fetchClinics();
    console.log(this.clinics);
  }

  fetchClinics() {
    this.clinicService.getClinics().subscribe({
      next: (data) => {
        this.clinics = data;
<<<<<<< HEAD
=======

        this.clinics.forEach((clinic)=>{
          this.fetchDoctors(clinic)
        })
>>>>>>> ac6fd5c77964ebac69c618981232966f8798795d
      },
      error: (err) => {
        console.error('Eroare la preluarea clinicilor', err);
      },
    });
  }
<<<<<<< HEAD
=======

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
>>>>>>> ac6fd5c77964ebac69c618981232966f8798795d
}
