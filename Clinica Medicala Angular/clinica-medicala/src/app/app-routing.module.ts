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

const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' }, 
  { path: 'home', component: HomeComponent }, 
  {
    path: 'clinics',
    component: ClinicsComponent,
    children: [
      { path: 'add-clinic', component: AddClinicComponent },
      { path: 'clinic-details/:id', component: ClinicDetailsComponent},
    ]
  },
  {path: 'doctors', component: DoctorsComponent,
  },
  {path: 'specializations', component: SpecializationsComponent},
  {path: 'patients', component: PatientsComponent},
  {path: 'medical-services', component: MedicalServicesComponent},
  {path: 'appointments', component: AppointmentsComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
