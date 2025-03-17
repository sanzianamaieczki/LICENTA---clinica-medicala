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
<<<<<<< HEAD
=======
import { MedicalServicesComponent } from './components/doctors/medical-services/medical-services.component';
>>>>>>> ac6fd5c77964ebac69c618981232966f8798795d

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
<<<<<<< HEAD
=======
    MedicalServicesComponent,
>>>>>>> ac6fd5c77964ebac69c618981232966f8798795d
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent],
  
})
export class AppModule { }
