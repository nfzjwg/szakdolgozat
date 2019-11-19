import { Component, OnInit } from '@angular/core';
import { Motobike } from 'src/app/classes/Motobike';
import { UserService } from 'src/app/services/User/user.service';
import { MotobikeService } from 'src/app/services/Motobike/motobike.service';
import { RentService } from 'src/app/services/Rent/rent.service';
import { Router } from '@angular/router';
import { MessageService } from 'src/app/services/Message/message.service';
import { FavouriteService } from 'src/app/services/Favourite/favourite.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-motorcycle',
  templateUrl: './motorcycle.component.html',
  styleUrls: ['./motorcycle.component.css']
})
export class MotorcycleComponent implements OnInit {
  motobikes : Motobike[]
  private userService :UserService
  private rentService : RentService
  private messageService : MessageService
  private favouriteService : FavouriteService
  constructor(private router : Router, private motobikeService : MotobikeService, rentService : RentService,
     userService :UserService, messageService : MessageService, favouriteService : FavouriteService, private toastrService : ToastrService ) { 
    this.userService = userService;
    this.rentService = rentService;
    this.messageService = messageService;
    this.favouriteService = favouriteService;
  }
  async ngOnInit() {
    this.motobikes = await this.motobikeService.getMotobikes()
    console.log(this.motobikes)
  }
  rentBike(id :number){
    this.rentService.addRent(0,id);
    console.log("adding rent")
    window.location.reload();
  }
  addBikeToFavourites(id:number){
    this.favouriteService.addFavourite(0, id).then((response)=>{
      if(response){
        this.toastrService.success("Success!", "Adding to favourites")
      }else{
        this.toastrService.error("Error!","Adding to favourites")
      }

    })
   
  }
  sendMessage(id : number){
    this.messageService.id = id;
    console.log("Send message here."+this.messageService.id)
    this.router.navigate(['sendMessage']);
  }
  deleteBike(id : number){
    this.motobikeService.deleteMotobike(id)
  }
  async edit(id : number){
    this.motobikeService.bike = await this.motobikeService.getMotobike(id);
    this.router.navigate(['editMotobike']);
  }
}
