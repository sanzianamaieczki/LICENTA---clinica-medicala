import { Component, OnInit } from '@angular/core';
import { DoctorService } from '../../../services/doctor.service';
import { MedicalServicesModel } from '../../../models/medical-services.model';

@Component({
  selector: 'app-medical-services',
  templateUrl: './medical-services.component.html',
  styleUrl: './medical-services.component.css'
})
export class MedicalServicesComponent implements OnInit {

  medicalServices: MedicalServicesModel[] = []; 
  constructor(
    private readonly doctorService: DoctorService,
  ) {}

  ngOnInit(): void {
    this.fetchMedicalServices();
  }

  fetchMedicalServices(){
    this.doctorService.getMedicalServices().subscribe({
      next: (data) => {
        this.medicalServices = data
        console.log('Servicii medicale: ', data);
      },
      error: (err) => {
        console.log('Eroare la preluarea serviciilor medicale', err);
      }
    })
  }
}
