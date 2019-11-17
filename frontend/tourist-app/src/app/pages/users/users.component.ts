import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/User/user.service';
import { User } from 'src/app/classes/User';
import { MotobikeService } from 'src/app/services/Motobike/motobike.service';
import { CarService } from 'src/app/services/Car/car.service';
import { RentService } from 'src/app/services/Rent/rent.service';
import { FavouriteService } from 'src/app/services/Favourite/favourite.service';
import { MessageService } from 'src/app/services/Message/message.service';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {
  users : User[]
  constructor(private userService :UserService,private bikeService : MotobikeService,
    private carService : CarService, private rentService : RentService,
    private favouriteService :FavouriteService,private messageService : MessageService) { }



 async ngOnInit() {
    this.users = await this.userService.getUsers()
  }
  deleteUser(id: number){
    //this.carService.deleteCarByOwner(id)
    this.bikeService.deleteMotobikeByOwner(id)
    this.rentService.deleteRentByOwner(id)
    this.favouriteService.deleteFavouriteByOwner(id)
    this.messageService.deleteMessageBySender(id)
    this.userService.deleteUser(id)
    //window.location.reload();

  }

}
