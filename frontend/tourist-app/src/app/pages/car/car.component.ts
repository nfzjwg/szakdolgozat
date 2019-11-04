import { Component, OnInit } from '@angular/core';
import { Car } from 'src/app/classes/Car';
import { CarService } from 'src/app/services/Car/car.service';
import { UserService } from 'src/app/services/User/user.service';
import { RentService } from 'src/app/services/Rent/rent.service';

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
  constructor(private carService : CarService, rentService : RentService,userService :UserService) { 
    this.userService = userService;
    this.rentService = rentService;
  }
  async ngOnInit() {
    this.cars = await this.carService.getCars()
    console.log(this.cars)
  }
  rentCar(id :number){
  
    this.rentService.addRent(id,0);
    console.log(this.carService.getCar(id))
  }
}
