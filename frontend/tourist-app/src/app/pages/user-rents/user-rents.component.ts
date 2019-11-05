import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/User/user.service';
import { RentService } from 'src/app/services/Rent/rent.service';
import { Rent } from 'src/app/classes/Rent';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-rents',
  templateUrl: './user-rents.component.html',
  styleUrls: ['./user-rents.component.css']
})
export class UserRentsComponent implements OnInit {
  constructor(private router : Router,private userService : UserService, private rentService : RentService) {
  }
    
  rents : Rent[]

  async ngOnInit() {
    this.rents = await this.rentService.getRentsByUser(this.userService.user.id)
  }
  returnRentedItem(id : number){
    this.rentService.returnRentedItem(id)
    this.router.navigate(['home']);
  }
}