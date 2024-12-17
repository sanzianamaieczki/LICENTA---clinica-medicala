import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ClinicsComponent } from './components/clinics/clinics.component';
import { HttpClientModule } from '@angular/common/http';
import { HomeComponent } from './components/home/home.component';
import { AddClinicComponent } from './components/add-clinic/add-clinic.component';
import { FormsModule } from '@angular/forms';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { SpecializationsComponent } from './components/specializations/specializations.component';
import { DoctorsComponent } from './components/doctors/doctors.component';

@NgModule({
  declarations: [
    AppComponent,
    ClinicsComponent,
    HomeComponent,
    AddClinicComponent,
    HeaderComponent,
    FooterComponent,
    SpecializationsComponent,
    DoctorsComponent
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
