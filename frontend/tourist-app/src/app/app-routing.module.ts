import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomePageComponent } from './pages/home-page/home-page.component';
import { RegisterPanelComponent } from './pages/register-panel/register-panel.component';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { CarComponent } from './pages/car/car.component';
import { UploadcarComponent } from './pages/uploadcar/uploadcar.component';



const routes: Routes = [
  { path : "users/register", component : RegisterComponent},
  { path : "home", component : HomePageComponent},
  { path : "users/login", component : LoginComponent},
  { path : "cars" , component : CarComponent},
  { path : "cars/upload" , component : UploadcarComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes), RouterModule.forRoot(routes, { onSameUrlNavigation: 'reload' })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
