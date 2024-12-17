import { Component, OnInit } from '@angular/core';
import { DoctorService} from '../../services/doctor.service'

@Component({
  selector: 'app-doctors',
  templateUrl: './doctors.component.html',
  styleUrls: ['./doctors.component.css']
})
export class DoctorsComponent implements OnInit{

  doctors: any[] = [];

  constructor(private doctorService: DoctorService){}

  ngOnInit(): void{
    this.fetchDoctors();
  }

  fetchDoctors(){
    this.doctorService.getDoctors().subscribe({
      next: (data) =>{
        this.doctors = data;
        console.log('doctori: '+this.doctors);
      },
      error: (err) => {
        console.log('Eroare la preluarea doctorilor', err);
      }
    })
  }

}
