import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ClinicsComponent } from './components/clinic/clinics.component';
import { HttpClientModule } from '@angular/common/http';
import { HomeComponent } from './components/home/home.component';
import { AddClinicComponent } from './components/clinic/add-clinic/add-clinic.component';
import { FormsModule } from '@angular/forms';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { SpecializationsComponent } from './components/specializations/specializations.component';
import { DoctorsComponent } from './components/doctors/doctors.component';
import { PatientsComponent } from './components/patients/patients.component';
import { MedicalServicesComponent } from './components/doctors/medical-services/medical-services.component';
import { AppointmentsComponent } from './components/appointments/appointments/appointments.component';
import { ClinicDetailsComponent } from './components/clinic/clinic-details/clinic-details.component';
import { DoctorDetailsComponent } from './components/doctors/doctor-details/doctor-details.component';
import { FullCalendarModule } from '@fullcalendar/angular'; 

@NgModule({
  declarations: [
    AppComponent,
    ClinicsComponent,
    HomeComponent,
    AddClinicComponent,
    HeaderComponent,
    FooterComponent,
    SpecializationsComponent,
    DoctorsComponent,
    PatientsComponent,
    MedicalServicesComponent,
    AppointmentsComponent,
    ClinicDetailsComponent,
    DoctorDetailsComponent,
    AddClinicComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    FullCalendarModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
  
})
export class AppModule { }
