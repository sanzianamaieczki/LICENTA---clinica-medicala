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

        this.specializations.forEach((specialization)=>{
          this.fetchDoctors(specialization)
          console.log(specialization.specialization_name)
        })
        console.log('specializari:' )
        console.log(this.specializations)

      },
      error: (err) =>{
        console.log('Eroare la primirea specializarilor: ', err);
      }
    }) 
  }

  fetchDoctors(specialization: SpecializationModel){
    this.specializationService.getDoctorsBySpecializationId(specialization.id_specialization).subscribe({
      next: (doctors) =>{
        this.specializations = this.specializations.map(s =>{
          if(s.id_specialization === specialization.id_specialization){
            return {...s, doctors: doctors}
          }
          else{
            return s
          }
        })
      },
      error: (errors) =>{
        console.error(`Eroare la preluarea doctorilor de la specializarea: ${specialization.specialization_name}`, errors)
        this.specializations = this.specializations.map(s=>{
          if(s.id_specialization === specialization.id_specialization){
            return {...s,doctors:[]}
          }
          else{
            return s;
          }
        })
      }
    })
  }

}
