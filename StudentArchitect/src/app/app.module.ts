import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { PricingComponent } from './pricing/pricing.component';
import { AppRoutingModule } from './/app-routing.module';
import { HomeComponent } from './home/home.component';
import { ClassComponent } from './class/class.component';
import { SubjectDetailComponent } from './subject-detail/subject-detail.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    PricingComponent,
    HomeComponent,
    ClassComponent,
    SubjectDetailComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
