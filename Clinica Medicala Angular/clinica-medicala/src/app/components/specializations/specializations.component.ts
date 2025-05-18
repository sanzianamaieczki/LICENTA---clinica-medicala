import { Component, OnInit } from '@angular/core';
import { SpecializationService } from '../../services/specialization.service';
import { SpecializationModel } from '../../models/specialization.model';
import { ActivatedRoute } from '@angular/router';
import { DoctorModel } from '../../models/doctor.model';
import { DoctorService } from '../../services/doctor.service';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-specializations',
  templateUrl: './specializations.component.html',
  styleUrls: ['./specializations.component.css']
})
export class SpecializationsComponent implements OnInit{

  specializations: SpecializationModel[] = [];
  clinicId: number = 0;
  doctors: DoctorModel[] = [];
  showDoctorsFor: number = 0;

  constructor(private readonly specializationService: SpecializationService,
    private readonly doctorService: DoctorService,
    private readonly route: ActivatedRoute
    
  ){}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.clinicId = params['clinic'] ? +params['clinic'] : 0;
      console.log('Clinic ID:', this.clinicId);
      this.fetchSpecializations()
    });
  }

  fetchSpecializations(){

    forkJoin({
      specializations: this.specializationService.getSpecializations({is_deleted: false}),
      doctors: this.doctorService.getDoctors({is_deleted: false})
    }).subscribe({
      next: ({specializations, doctors}) => {
        this.specializations = specializations;
        this.doctors = this.clinicId ? doctors.filter(doctor => doctor.id_clinic === this.clinicId) : doctors;
        console.log('Doctors:', this.doctors);
      },
      error: (err) => {
        console.error('Eroare la preluarea specializarilor', err);
      }
    });
   
}

toogleDoctors(specializationId: number) {
  this.showDoctorsFor = this.showDoctorsFor === specializationId ? 0 : specializationId;
}

}
