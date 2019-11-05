import { Component, OnInit } from '@angular/core';
import { Rent } from 'src/app/classes/Rent';
import { UserService } from 'src/app/services/User/user.service';
import { RentService } from 'src/app/services/Rent/rent.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-rental',
  templateUrl: './rental.component.html',
  styleUrls: ['./rental.component.css']
})
export class RentalComponent implements OnInit {
  rents : Rent[]
  private userService : UserService
  private rentService : RentService
  constructor( private router : Router ,rentService : RentService,  userService : UserService) {
    this.rentService = rentService;
    this.userService = userService;
   }

  async ngOnInit() {
    this.rents = await this.rentService.getRents();
    console.log(this.rents)
  }
  returnRentedItem(id : number){
    this.rentService.returnRentedItem(id)
    this.router.navigate(['home']);
  }
}
