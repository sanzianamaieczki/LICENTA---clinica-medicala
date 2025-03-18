import { Component, OnInit } from '@angular/core';
import { SpecializationService } from '../../services/specialization.service';
import { SpecializationModel } from '../../models/specialization.model';

@Component({
  selector: 'app-specializations',
  templateUrl: './specializations.component.html',
  styleUrls: ['./specializations.component.css']
})
export class SpecializationsComponent implements OnInit{

  specializations: SpecializationModel[] = [];

  constructor(private readonly specializationService: SpecializationService
  ){}

  ngOnInit(): void {
    this.fetchSpecializations()
  }

  fetchSpecializations(){
    this.specializationService.getSpecializations({is_deleted: false}).subscribe({
      next: (data) =>{
        this.specializations = data;
      },
      error: (err) =>{
        console.log('Eroare la primirea specializarilor: ', err);
      }
    }) 
  }

}
