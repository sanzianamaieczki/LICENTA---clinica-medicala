import { Component, OnInit } from '@angular/core';
import { SpecializationService } from '../../services/specialization.service';

@Component({
  selector: 'app-specializations',
  templateUrl: './specializations.component.html',
  styleUrls: ['./specializations.component.css']
})
export class SpecializationsComponent implements OnInit{

  specializations: any[] = [];

  constructor(private specializationService: SpecializationService){}

  ngOnInit(): void {
    this.fetchSpecializations()
  }

  fetchSpecializations(){
    this.specializationService.getSpecializations().subscribe({
      next: (data) =>{
        this.specializations = data;
      },
      error: (err) =>{
        console.log('Eroare la primirea specializarilor: ', err);
      }
    }) 
  }

}
