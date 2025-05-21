import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { ClinicsComponent } from './components/clinic/clinics.component';
import { AddClinicComponent } from './components/clinic/add-clinic/add-clinic.component';
import { DoctorsComponent } from './components/doctors/doctors.component';
import { SpecializationsComponent } from './components/specializations/specializations.component';
import { PatientsComponent } from './components/patients/patients.component';
import { MedicalServicesComponent } from './components/doctors/medical-services/medical-services.component';
import { AppointmentsComponent } from './components/appointments/appointments/appointments.component';
import { ClinicDetailsComponent } from './components/clinic/clinic-details/clinic-details.component';
import { DoctorDetailsComponent } from './components/doctors/doctor-details/doctor-details.component';
import { AddAppointmentComponent } from './components/appointments/add-appointment/add-appointment.component';

const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' }, 
  { path: 'home', component: HomeComponent }, 
  {
    path: 'clinics',
    component: ClinicsComponent,
    children: [
      { path: 'add-clinic', component: AddClinicComponent },
      { path: ':id', component: ClinicDetailsComponent},
    ]
  },
  {path: 'doctors', component: DoctorsComponent,
    children: [
      {path: ":id", component: DoctorDetailsComponent}
    ]
  },
  {path: 'specializations', component: SpecializationsComponent},
  {path: 'patients', component: PatientsComponent},
  {path: 'medical-services', component: MedicalServicesComponent},
  {path: 'appointments', component: AppointmentsComponent,
    children: [
      {path: 'add-appointment', component: AddAppointmentComponent}
    ]
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
