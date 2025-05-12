import { Component, OnInit } from '@angular/core';
import { SpecializationService } from '../../services/specialization.service';
import { SpecializationModel } from '../../models/specialization.model';
import { ActivatedRoute } from '@angular/router';
import { DoctorModel } from '../../models/doctor.model';
import { DoctorService } from '../../services/doctor.service';

@Component({
  selector: 'app-specializations',
  templateUrl: './specializations.component.html',
  styleUrls: ['./specializations.component.css']
})
export class SpecializationsComponent implements OnInit{

  specializations: SpecializationModel[] = [];
  clinicId: number = 0;

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

    this.doctorService.getDoctors({is_deleted: false}).subscribe({
      next: (data: DoctorModel[]) =>{
        const doctors = this.clinicId ? data.filter(doctor => doctor.id_clinic === this.clinicId) : data;
        const specIds = Array.from(new Set(doctors.map((doctor: DoctorModel) => doctor.id_specialization)));
        
        this.specializationService.getSpecializations({is_deleted: false}).subscribe({
          next: (data: SpecializationModel[]) =>{
            this.specializations = specIds.length ? data.filter((spec: SpecializationModel) => specIds.includes(spec.id_specialization)) : data;
          },
          error: (err) => {
            console.log('Eroare la preluarea specializarilor', err);
            this.specializations = [];
          }
        });
    },
      error: (err) => {
        console.log('Eroare la preluarea doctorilor', err);
        this.specializations = [];
  }});
}


}
