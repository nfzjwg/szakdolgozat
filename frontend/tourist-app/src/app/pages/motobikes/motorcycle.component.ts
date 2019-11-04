import { Component, OnInit } from '@angular/core';
import { Motobike } from 'src/app/classes/Motobike';
import { UserService } from 'src/app/services/User/user.service';
import { MotobikeService } from 'src/app/services/Motobike/motobike.service';
import { RentService } from 'src/app/services/Rent/rent.service';

@Component({
  selector: 'app-motorcycle',
  templateUrl: './motorcycle.component.html',
  styleUrls: ['./motorcycle.component.css']
})
export class MotorcycleComponent implements OnInit {
  motobikes : Motobike[]
  private userService :UserService
  private rentService : RentService
  constructor(private motobikeService : MotobikeService, rentService : RentService, userService :UserService) { 
    this.userService = userService;
    this.rentService = rentService;
  }
  async ngOnInit() {
    this.motobikes = await this.motobikeService.getMotobikes()
    console.log(this.motobikes)
  }
  rentBike(id :number){
    this.rentService.addRent(0,id);
    console.log(this.motobikeService.getMotobike(id))
  }
}
