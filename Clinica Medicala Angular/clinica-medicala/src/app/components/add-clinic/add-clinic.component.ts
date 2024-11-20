import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ClinicService } from '../../services/clinic.service';

@Component({
  selector: 'app-add-clinic',
  templateUrl: './add-clinic.component.html',
  styleUrls: ['./add-clinic.component.css']
})
export class AddClinicComponent {
  clinic = {
    clinic_name: '',
    clinic_phone: '',
    clinic_address: ''
  };

  constructor(private clinicService: ClinicService, private router: Router) {}

  submitClinic() {
    this.clinicService.addClinic(this.clinic).subscribe({
      next: () => {
        alert('Clinica a fost adăugată cu succes!');
        this.router.navigate(['/clinics']);
      },
      error: (err) => {
        console.error('Eroare la adăugarea clinicii', err);
        alert('A apărut o eroare la salvarea clinicii.');
      }
    });
  }
}
