import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient, HttpErrorResponse } from "@angular/common/http";
import { UserService, httpOptions } from '../User/user.service';
import { Favourite } from 'src/app/classes/Favourite';

@Injectable({
  providedIn: 'root'
})
export class FavouriteService {

  constructor(private http : HttpClient, private userService : UserService) { }
  async getFavouritesByUser(id: number) {
		return this.http.get<Favourite>(
			`http://localhost:8080/favourites/by-user?user=${id}`,
			httpOptions
		).toPromise().catch((error: HttpErrorResponse) => {
			return null;
		});
	}

	async addFavourite(carID : number, motobikeID : number){
	return this.http.post(`http://localhost:8080/favourites/add?user_id=${this.userService.user.id}&car_id=${carID}&motobike_id=${motobikeID}`,
	{}, httpOptions).toPromise();
	
	}
	
	  
}
