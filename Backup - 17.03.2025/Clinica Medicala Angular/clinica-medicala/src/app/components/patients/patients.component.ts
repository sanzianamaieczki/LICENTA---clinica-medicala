import { Component, OnInit } from '@angular/core';
import { PatientsService } from '../../services/patient.service';


@Component({
  selector: 'app-patients',
  templateUrl: './patients.component.html',
  styleUrl: './patients.component.css'
})
export class PatientsComponent implements OnInit {

  patients: any[] = [];

  constructor(private patientsService: PatientsService){}

  ngOnInit(): void {
    this.fetchPatients()
  }

  fetchPatients(){
    this.patientsService.getPatients().subscribe({
      next: (data) =>{
        this.patients = data;
      },
      error: (err) =>{
        console.log('Eroare la primirea pacientilor: ', err);
      }
    }) 
  }
}