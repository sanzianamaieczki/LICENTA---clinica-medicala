import { Component, OnInit } from '@angular/core';
import { DoctorService} from '../../services/doctor.service'
import { DoctorModel } from '../../models/doctor.model';
<<<<<<< HEAD
import { DoctorScheduleModel } from '../../models/doctor-schedule.model';
import { SpecializationService } from '../../services/specialization.service';
import { SpecializationModel } from '../../models/specialization.model';
=======
import { SpecializationService } from '../../services/specialization.service';
>>>>>>> ac6fd5c77964ebac69c618981232966f8798795d
import { ClinicService } from '../../services/clinic.service';

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
<<<<<<< HEAD
    private doctorService: DoctorService,
    private specializationService: SpecializationService,
    private clinicService: ClinicService
=======
    private readonly doctorService: DoctorService,
    private readonly specializationService: SpecializationService,
    private readonly clinicService: ClinicService
>>>>>>> ac6fd5c77964ebac69c618981232966f8798795d
  ){}

  ngOnInit(): void{
    this.fetchDoctors();
  }

  fetchDoctors(){
    this.doctorService.getDoctors({is_deleted: false}).subscribe({
      next: (data) =>{
        this.doctors = data;

        this.doctors.forEach((doctor) => {
<<<<<<< HEAD
          this.fetchDoctorSchedule(doctor);
          this.fetchSpecialization(doctor);
          this.fetchClinic(doctor);
=======
          //this.fetchDoctorSchedule(doctor);
          //this.fetchSpecialization(doctor);
          //this.fetchClinic(doctor);
          //this.fetchMedicalServices(doctor);
>>>>>>> ac6fd5c77964ebac69c618981232966f8798795d
        })

        console.log('doctori: '+this.doctors);
      },
      error: (err) => {
        console.log('Eroare la preluarea doctorilor', err);
      }
    })
  }

  fetchDoctorSchedule(doctor: DoctorModel){
    this.doctorService.getDoctorSchedule(doctor.id_doctor).subscribe({
      next: (schedule) =>{
        this.doctors = this.doctors.map(d => {
          if(d.id_doctor === doctor.id_doctor){
            return {...d, schedule: schedule}
          }
          else{
            return d
          }
        })
      },
      error: (error) =>{
        console.error(`Eroare la preluarea programului doctorului: ${doctor.id_doctor}: `, error)    
        this.doctors = this.doctors.map(d => {
          if(d.id_doctor === doctor.id_doctor){
            return {...d, schedule: []}
          }
          else{
            return d;
          }
        })          
      }
    })
  }

  fetchSpecialization(doctor:DoctorModel){
    this.specializationService.getSpecializationById(doctor.id_specialization).subscribe({
      next: (specialization) =>{
        this.doctors = this.doctors.map(d => {
          if(d.id_specialization === doctor.id_specialization){
            return {...d, specialization_name: specialization.specialization_name}
          }
          else{
            return d
          }
        })
      },
      error: (error) =>{
        console.error(`Eroare la preluarea specializarii doctorului: ${doctor.id_doctor}: `, error)    
        this.doctors = this.doctors.map(d => {
          if(d.id_specialization === doctor.id_specialization){
            return {...d, specialization_name: ''}
          }
          else{
            return d;
          }
        })     
      } 
    })
  }

  fetchClinic(doctor:DoctorModel){
    this.clinicService.getClinicById(doctor.id_clinic).subscribe({
      next: (clinic) =>{
        this.doctors = this.doctors.map(d => {
          if(d.id_clinic === doctor.id_clinic){
            return {...d, clinic_name: clinic.clinic_name}
          }
          else{
            return d
          }
        })
      },
      error: (error) =>{
        console.error(`Eroare la preluarea clinicii doctorului: ${doctor.id_doctor}: `, error)    
        this.doctors = this.doctors.map(d => {
          if(d.id_clinic === doctor.id_clinic){
            return {...d, clinic_name: ''}
          }
          else{
            return d;
          }
        })     
      } 
    })
  }
<<<<<<< HEAD
=======

  fetchMedicalServices(doctor:DoctorModel){
    this.doctorService.getDoctorMedicalServices(doctor.id_clinic).subscribe({
      next: (medicalService) =>{
        this.doctors = this.doctors.map(d => {
          if(d.id_doctor === doctor.id_doctor){
            return {...d, medical_services: medicalService}
          }
          else{
            return d
          }
        })
      },
      error: (error) =>{
        console.error(`Eroare la preluarea serviciilor medicale ale doctorului: ${doctor.id_doctor}: `, error)    
        this.doctors = this.doctors.map(d => {
          if(d.id_doctor === doctor.id_doctor){
            return {...d, medical_services: []}
          }
          else{
            return d;
          }
        })     
      } 
    })
  }
>>>>>>> ac6fd5c77964ebac69c618981232966f8798795d
}
