import { Component, OnInit } from '@angular/core';
import { Car } from 'src/app/classes/Car';
import { CarService } from 'src/app/services/Car/car.service';
import { UserService } from 'src/app/services/User/user.service';

@Component({
  selector: 'app-car',
  templateUrl: './car.component.html',
  styleUrls: ['./car.component.css']
})
export class CarComponent implements OnInit {
  cars : Car[]
  private userService :UserService
  constructor(private carService : CarService, userService :UserService) { 
    this.userService = userService
  }
  async ngOnInit() {
    this.cars = await this.carService.getCars()
    console.log(this.cars)
  }
  
}
