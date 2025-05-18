import { Component, OnInit } from '@angular/core';
import { DoctorService} from '../../services/doctor.service'
import { DoctorModel } from '../../models/doctor.model';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-doctors',
  templateUrl: './doctors.component.html',
  styleUrls: ['./doctors.component.css']
})
export class DoctorsComponent implements OnInit{

  doctors: DoctorModel[] = [];
  clinicId: number = 0;

  dayMapping: {[key: string]: string} = {
    monday: 'Luni',
    tuesday: 'Marti',
    wednesday: 'Miercuri',
    thursday: 'Joi',
    friday: 'Vineri',
    saturday: 'Sambata',
    sunday: 'Duminica'
  }

  constructor(
    private readonly doctorService: DoctorService,
    private readonly route: ActivatedRoute,
    private readonly router: Router
  ){}

  ngOnInit(): void{
    this.route.queryParams.subscribe(params => {
      this.clinicId = params['clinic'] ? +params['clinic'] : 0;
      this.fetchDoctors();
    });
  }

  fetchDoctors(){
    this.doctorService.getDoctors({is_deleted: false}).subscribe({
      next: (data: DoctorModel[]) =>{
        this.doctors = this.clinicId ? data.filter(doctor => doctor.id_clinic === this.clinicId) : data;
      },
      error: (err) => {
        console.log('Eroare la preluarea doctorilor', err);
      }
    })
  }
  showSchedule(doctorId: number) {
    this.router.navigate(['/doctors',doctorId], {
      queryParams: {schedule: true },
    });
  }

  showServices(doctorId: number) {
    this.router.navigate(['/doctors', doctorId], {
      queryParams: { services: true},
    });
  }

}