import { Component, OnInit } from '@angular/core';
import { ClinicModel } from '../../models/clinic.model';
import { ClinicService } from '../../services/clinic.service';
import { DoctorModel } from '../../models/doctor.model';
import { DoctorService } from '../../services/doctor.service';
import { SpecializationModel } from '../../models/specialization.model';
import { SpecializationService } from '../../services/specialization.service';
import { MedicalServicesModel } from '../../models/medical-services.model';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  clinics: ClinicModel[] = [];
  showClinicsDropdown = false;

  doctors: DoctorModel[] = [];
  showDoctorsDropdown = false;

  specializations: SpecializationModel[] = [];
  showSpecializationsDropdown = false;

  services: MedicalServicesModel[] = [];
  showServicesDropdown = false;

  constructor(private clinicService: ClinicService,
    private doctorService: DoctorService,
    private specializationService: SpecializationService
  ) {}

  ngOnInit(): void {
    this.clinicService.getClinics().subscribe({
      next: data => this.clinics = data,
      error: err => console.error('Eroare la preluarea clinicilor:', err)
    });

    this.doctorService.getDoctors().subscribe({
      next: data => this.doctors = data,
      error: err => console.error('Eroare la preluarea doctorilor:', err)
    });

    this.specializationService.getSpecializations().subscribe({
      next: data => this.specializations = data,
      error: err => console.error('Eroare la preluarea specializarilor:', err)
    });

    this.doctorService.getMedicalServices().subscribe({
      next: data => this.services = data,
      error: err => console.error('Eroare la preluarea serviciilor medicale:', err)
    });
  }


}
