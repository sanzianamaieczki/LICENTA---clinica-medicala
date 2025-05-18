import { Component, OnInit } from '@angular/core';
import { ClinicModel } from '../../../models/clinic.model';
import { DoctorModel } from '../../../models/doctor.model';
import { SpecializationModel } from '../../../models/specialization.model';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { ClinicService } from '../../../services/clinic.service';
import { DoctorService } from '../../../services/doctor.service';
import { SpecializationService } from '../../../services/specialization.service';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-clinic-details',
  templateUrl: './clinic-details.component.html',
  styleUrl: './clinic-details.component.css'
})
export class ClinicDetailsComponent implements OnInit {

  clinicId!: number;
  clinic?: ClinicModel;

  doctors: DoctorModel[] = [];
  specializations: SpecializationModel[] = [];

  showDoctors = false;
  showSpecializations = false;

  constructor(private readonly route: ActivatedRoute,
    private readonly router: Router,
    private readonly clinicService: ClinicService,
    private readonly doctorService: DoctorService,
    private readonly specializationService: SpecializationService
  ) { }

  ngOnInit(): void {
    this.route.paramMap
    .subscribe((params: ParamMap) => {
      const id = Number(params.get('id'));
      this.clinicId = id;
      this.fetchClinicDetails(id);
    });

    this.route.queryParams.subscribe((params) => {
      this.showDoctors = params['doctors'] === 'true';
      this.showSpecializations = params['specializations'] === 'true';
    });
  }

  fetchClinicDetails(clinicId: number) {

    forkJoin({
      clinic: this.clinicService.getClinicById(clinicId),
      doctors:this.doctorService.getDoctors({ is_deleted: 'false' }),
      specs:this.specializationService.getSpecializations({ is_deleted: 'false' })
    }).subscribe({
      next: ({ clinic, doctors, specs }) => {
        this.clinic = clinic;
        this.doctors = doctors.filter(doctor => doctor.id_clinic === clinicId);
        const specIds = Array.from(new Set(this.doctors.map(doctor => doctor.id_specialization)));
        this.specializations = specIds.length ? specs.filter(spec => specIds.includes(spec.id_specialization)) : [];
      },
      error: (err) => {
        console.error('Eroare la preluarea clinicii:', err);
      }
    });
  }

  toogleDoctors() {
    this.router.navigate(['/clinics', this.clinicId], {
      queryParams: { doctors: !this.showDoctors },  
    });
  }
  toogleSpecializations() {
    this.router.navigate(['/clinics', this.clinicId], {
      queryParams: { specializations: !this.showSpecializations },  
    });
  }
}
