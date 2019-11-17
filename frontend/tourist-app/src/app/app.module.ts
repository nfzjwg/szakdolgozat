import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomePageComponent } from './pages/home-page/home-page.component';
import { HeaderComponent } from './pages/header/header.component';
import { TopPanelComponent } from './pages/top-panel/top-panel.component';
import { RegisterComponent } from './pages/register/register.component';
import { RegisterPanelComponent } from './pages/register-panel/register-panel.component';
import { HttpClientModule } from "@angular/common/http";
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from "@angular/common";
import { LoginComponent } from './pages/login/login.component';
import { CarComponent } from './pages/car/car.component';
import { UploadcarComponent } from './pages/uploadcar/uploadcar.component';
import { MotorcycleComponent } from './pages/motobikes/motorcycle.component';
import { UploadmotobikeComponent } from './pages/uploadmotobike/uploadmotobike.component';
import { CarRentComponent } from './pages/car-rent/car-rent.component';
import { RentalComponent } from './pages/rental/rental.component';
import { UserRentsComponent } from './pages/user-rents/user-rents.component';
import { ProfilePanelComponent } from './pages/profile-panel/profile-panel.component';
import { ProfileDataComponent } from './pages/profile-data/profile-data.component';
import { ProfileMessagesComponent } from './pages/profile-messages/profile-messages.component';
import { ProfileFavouritesComponent } from './pages/profile-favourites/profile-favourites.component';
import { MessageSenderFormComponent } from './pages/message-sender-form/message-sender-form.component';
import { ReceiptComponent } from './pages/receipt/receipt.component';
import { RatingModule } from 'ng-starrating';
import { CarEditComponent } from './pages/car-edit/car-edit.component';
import { UsersComponent } from './pages/users/users.component';

@NgModule({
  declarations: [
    AppComponent,
    HomePageComponent,
    HeaderComponent,
    TopPanelComponent,
    RegisterComponent,
    RegisterPanelComponent,
    LoginComponent,
    CarComponent,
    UploadcarComponent,
    MotorcycleComponent,
    UploadmotobikeComponent,
    CarRentComponent,
    RentalComponent,
    UserRentsComponent,
    ProfilePanelComponent,
    ProfileDataComponent,
    ProfileMessagesComponent,
    ProfileFavouritesComponent,
    MessageSenderFormComponent,
    ReceiptComponent,
    CarEditComponent,
    UsersComponent
  ],
  imports: [
    
    BrowserModule,
    AppRoutingModule,
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    CommonModule,
    RatingModule
    
     
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
