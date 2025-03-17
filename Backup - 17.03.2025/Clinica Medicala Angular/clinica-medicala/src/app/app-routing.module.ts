import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { ClinicsComponent } from './components/clinic/clinics.component';
import { AddClinicComponent } from './components/clinic/add-clinic/add-clinic.component';
import { DoctorsComponent } from './components/doctors/doctors.component';
import { SpecializationsComponent } from './components/specializations/specializations.component';
import { PatientsComponent } from './components/patients/patients.component';
import { MedicalServicesComponent } from './components/doctors/medical-services/medical-services.component';

const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' }, 
  { path: 'home', component: HomeComponent }, 
  {
    path: 'clinics',
    component: ClinicsComponent,
    children: [
      { path: 'add-clinic', component: AddClinicComponent }
    ]
  },
  {path: 'doctors', component: DoctorsComponent,
  },
  {path: 'specializations', component: SpecializationsComponent},
  {path: 'patients', component: PatientsComponent},
  {path: 'medical-services', component: MedicalServicesComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
