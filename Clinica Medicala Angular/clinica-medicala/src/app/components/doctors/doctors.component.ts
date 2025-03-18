import { Component, OnInit } from '@angular/core';
import { DoctorService} from '../../services/doctor.service'
import { DoctorModel } from '../../models/doctor.model';

@Component({
  selector: 'app-doctors',
  templateUrl: './doctors.component.html',
  styleUrls: ['./doctors.component.css']
})
export class DoctorsComponent implements OnInit{

  doctors: DoctorModel[] = [];
  //doctorSchedule: DoctorScheduleModel[] = [];

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
  ){}

  ngOnInit(): void{
    this.fetchDoctors();
  }

  fetchDoctors(){
    this.doctorService.getDoctors({is_deleted: false}).subscribe({
      next: (data) =>{
        this.doctors = data;
      },
      error: (err) => {
        console.log('Eroare la preluarea doctorilor', err);
      }
    })
  }
}