import { Component, OnInit } from '@angular/core';
import { ClinicService } from '../../services/clinic.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-clinics',
  templateUrl: './clinics.component.html',
  styleUrls: ['./clinics.component.css'],
})
export class ClinicsComponent implements OnInit {
  clinics: any[] = [];

  constructor(private readonly clinicService: ClinicService,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.fetchClinics();
    console.log(this.clinics);
  }

  fetchClinics() {
    this.clinicService.getClinics().subscribe({
      next: (data) => {
        this.clinics = data;
      },
      error: (err) => {
        console.error('Eroare la preluarea clinicilor', err);
      },
    });
  }

  showDoctors(clinicId: number) {
    this.router.navigate(['/clinics', clinicId], {
      queryParams: {doctors: true},
    });
  }

  showSpecializations(clinicId: number) {
    this.router.navigate(['/clinics',clinicId], {
      queryParams: {specializations: true},
    });
  }

}
