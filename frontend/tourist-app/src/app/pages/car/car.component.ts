import { Component, OnInit } from '@angular/core';
import { Car } from 'src/app/classes/Car';
import { CarService } from 'src/app/services/Car/car.service';
import { UserService } from 'src/app/services/User/user.service';
import { RentService } from 'src/app/services/Rent/rent.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-car',
  templateUrl: './car.component.html',
  styleUrls: ['./car.component.css']
})
export class CarComponent implements OnInit {
  cars : Car[]
  car : Car
  private userService :UserService
  private rentService : RentService
  constructor(private router : Router, private carService : CarService, rentService : RentService,userService :UserService) { 
    this.userService = userService;
    this.rentService = rentService;
  }
  async ngOnInit() {
    this.cars = await this.carService.getCars()
    console.log(this.cars)
  }
  rentCar(id :number){
  
    this.rentService.addRent(id,0);
    if( this.userService.user.role == "ROLE_GUEST"){
      this.router.navigate(['rentsByUser']);  
    }else{
      this.router.navigate(['rents']);
    }
  }
  sendMessage(id : number){
    console.log("Send message here.")
  }
}
