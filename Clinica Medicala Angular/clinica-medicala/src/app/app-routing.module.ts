import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { ClinicsComponent } from './components/clinics/clinics.component';
import { AddClinicComponent } from './components/add-clinic/add-clinic.component';

const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' }, 
  { path: 'home', component: HomeComponent }, 
  {
    path: 'clinics',
    component: ClinicsComponent,
    children: [
      { path: 'add-clinic', component: AddClinicComponent }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
