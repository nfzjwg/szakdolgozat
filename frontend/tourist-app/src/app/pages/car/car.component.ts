import { Component, OnInit } from '@angular/core';
import { Car } from 'src/app/classes/Car';
import { CarService } from 'src/app/services/Car/car.service';
import { UserService } from 'src/app/services/User/user.service';
import { RentService } from 'src/app/services/Rent/rent.service';
import { Router } from '@angular/router';
import { FavouriteService } from 'src/app/services/Favourite/favourite.service';
import { MessageService } from 'src/app/services/Message/message.service';

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
  private messageService : MessageService
  private favouriteService : FavouriteService
  constructor(private router : Router, private carService : CarService, rentService : RentService, userService :UserService, messageService : MessageService, favouriteService : FavouriteService) { 
    this.userService = userService;
    this.rentService = rentService;
    this.messageService = messageService;
    this.favouriteService = favouriteService;
  }
  async ngOnInit() {
    this.cars = await this.carService.getCars()
    console.log(this.cars)
  }
  rentCar(id :number){
  
    this.rentService.addRent(id,0);
    window.location.reload();
  }
  addCarToFavourites(id : number){
    this.favouriteService.addFavourite(id, 0)
  }
  sendMessage(id : number){
    this.messageService.id = id;
    console.log("Send message here.")
    this.router.navigate(['sendMessage']);
  }
  delete(id : number){
    this.carService.deleteCar(id)
  }
  async edit(id : number){
    this.carService.id = id;
    this.carService.car =  await this.carService.getCar(id)
    this.router.navigate(['editCar']);
  }
}
