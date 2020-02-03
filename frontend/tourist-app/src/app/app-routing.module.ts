import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomePageComponent } from './pages/home-page/home-page.component';
import { RegisterPanelComponent } from './pages/register-panel/register-panel.component';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { CarComponent } from './pages/car/car.component';
import { UploadcarComponent } from './pages/uploadcar/uploadcar.component';
import { MotorcycleComponent } from './pages/motobikes/motorcycle.component';
import { UploadmotobikeComponent } from './pages/uploadmotobike/uploadmotobike.component';
import { RentalComponent } from './pages/rental/rental.component';
import { UserRentsComponent } from './pages/user-rents/user-rents.component';
import { ProfilePanelComponent } from './pages/profile-panel/profile-panel.component';
import { MessageSenderFormComponent } from './pages/message-sender-form/message-sender-form.component';
import { ReceiptComponent } from './pages/receipt/receipt.component';
import { UsersComponent } from './pages/users/users.component';
import { CarEditComponent } from './pages/car-edit/car-edit.component';
import { EditMotobikeComponent } from './pages/edit-motobike/edit-motobike.component';



const routes: Routes = [
  { path : "users/register", component : RegisterComponent},
  { path : "home", component : HomePageComponent},
  { path : "users/login", component : LoginComponent},
  { path : "cars" , component : CarComponent},
  { path : "cars/upload" , component : UploadcarComponent},
  { path : "motobikes" , component : MotorcycleComponent},
  { path : "motobikes/upload" , component : UploadmotobikeComponent},
  { path : "rents" , component : RentalComponent},
  { path : "rentsByUser" , component : UserRentsComponent},
  { path : "profile" , component : ProfilePanelComponent},
  { path : "sendMessage" , component : MessageSenderFormComponent},
  { path : "receipts" , component : ReceiptComponent},
  { path : "users" , component : UsersComponent},
  { path : "editCar" , component : CarEditComponent},
  { path : "editMotobike" , component : EditMotobikeComponent},
  { path : "", component : HomePageComponent}

  
];

@NgModule({
  imports: [RouterModule.forRoot(routes), RouterModule.forRoot(routes, { onSameUrlNavigation: 'reload' })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
