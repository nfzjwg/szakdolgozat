import { Injectable } from '@angular/core';
import { UserService,httpOptions } from '../User/user.service';
import { HttpHeaders, HttpClient, HttpResponse, HttpErrorResponse } from "@angular/common/http";
import { Rent } from 'src/app/classes/Rent';
import { formatDate } from '@angular/common';
import {CarService } from '../Car/car.service'
@Injectable({
  providedIn: 'root'
})
export class RentService {
  constructor(
    private http : HttpClient, private userService : UserService,
     private carService : CarService) {}
  async getRent(id: number) {
		return this.http.get<Rent>(
			`http://localhost:8080/rents/${id}`,
			httpOptions
		).toPromise().catch((error: HttpErrorResponse) => {
			return null;
		});
	}

  async getRents(){
    return this.http.get<Rent[]>(
      `http://localhost:8080/rents`,      
      httpOptions
    ).toPromise().catch((error:HttpErrorResponse) => {
      return new Array<Rent>()
    });
  }
  
  async getRentsByUser(userID : number){
    return this.http.get<Rent[]>(
      `http://localhost:8080/rents/by-user?user=${userID}`,      
      httpOptions
    ).toPromise().catch((error:HttpErrorResponse) => {
      return new Array<Rent>()
    });
	}


  async addRent(carID : number, motobikeID : number){
      const start = formatDate(Date.now(), "yyyy-MM-dd HH:mm:ss", "en-US");
      console.log(this.userService.user)
      console.log(carID)
      console.log(motobikeID)
    return this.http.post(`http://localhost:8080/rents/upload?user_id=${this.userService.user.id}&car_id=${carID}&motobike_id=${motobikeID}`,
      {
        "start" : start,
        "payed" : false
      }, httpOptions).toPromise();
      
    }
    async returnRentedItem(id :number) {
      const end = formatDate(Date.now(), "yyyy-MM-dd HH:mm:ss", "en-US");
      await this.http.put(`http://localhost:8080/rents/${id}`, 
      {
        "end" : end
      }, httpOptions).toPromise();
      return Promise.resolve(true);
    } catch (e) {
      console.log("hiba", e);
      return Promise.resolve(false);
    }
    async setPayed(id :number) {
      await this.http.put(`http://localhost:8080/rents/pay/${id}`, 
      {
        "payed" : true
      }, httpOptions).toPromise();
      return Promise.resolve(true);
    } 

    async deleteRentByOwner(id: number) {
      return this.http.delete<Rent>(
        `http://localhost:8080/rents/by-user/${id}`,
        httpOptions
      ).toPromise().catch((error: HttpErrorResponse) => {
        return null;
      });
    }
    
  }

