import { Component, OnInit } from '@angular/core';
import { Favourite } from 'src/app/classes/Favourite';
import { UserService } from 'src/app/services/User/user.service';
import { FavouriteService } from 'src/app/services/Favourite/favourite.service';
import { Router } from '@angular/router';
import { User } from 'src/app/classes/User';

@Component({
  selector: 'app-profile-favourites',
  templateUrl: './profile-favourites.component.html',
  styleUrls: ['./profile-favourites.component.css']
})
export class ProfileFavouritesComponent implements OnInit {
  favourites : Favourite[]
  users : User[]
  userFavourites : User[]
  user : User
  favourite : User
  private userService : UserService
  private favouriteService : FavouriteService
  constructor(private router : Router, userService : UserService,  favouriteService : FavouriteService) { 
    this.userService = userService;
    this.favouriteService = favouriteService;
    this.users = [];
    this.userFavourites = [];
  }

  async ngOnInit() {
    this.favourites = await this.favouriteService.getFavouritesByUser(this.userService.user.id);
   
  }

}
