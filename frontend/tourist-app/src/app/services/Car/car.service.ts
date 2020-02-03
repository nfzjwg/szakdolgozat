import { Injectable } from '@angular/core';
import { UserService, httpOptions } from '../User/user.service';
import { HttpHeaders, HttpClient, HttpResponse, HttpErrorResponse } from "@angular/common/http";
import { Car } from 'src/app/classes/Car';

@Injectable({
  providedIn: 'root'
})
export class CarService {
  id : number
  car : Car
  constructor(
    private http: HttpClient, private userService : UserService
  ) { }
  async getCar(id: number) {
		return this.http.get<Car>(
			`http://localhost:8080/cars/${id}`,
			httpOptions
		).toPromise().catch((error: HttpErrorResponse) => {
			return null;
		});
	}

  async getCars(){
    return this.http.get<Car[]>(
      `http://localhost:8080/cars`,      
      httpOptions
    ).toPromise().catch((error:HttpErrorResponse) => {
      return new Array<Car>()
    });
  }
  
  async getCarsByUser(userID : number){
    return this.http.get<Car[]>(
      `http://localhost:8080/cars/by-user?owner=${userID}`,      
      httpOptions
    ).toPromise().catch((error:HttpErrorResponse) => {
      return new Array<Car>()
    });
	}


  async addCar(manufacturer : string, model : string, doors : number,
     engine :  string, ccm: number, ac : boolean, rented  : boolean,  owner : number){
    return this.http.post(`http://localhost:8080/cars/upload?owner=`+owner,
      {
        "manufacturer" : manufacturer,
        "model" : model,
        "doors" : doors,
        "engine" : engine,
        "ccm" : ccm,
        "ac" : ac,
        "rented" : rented
       
      }, httpOptions).toPromise();
    }
    async deleteCar(id: number) {
      return this.http.delete<Car>(
        `http://localhost:8080/cars/${id}`,
        httpOptions
      ).toPromise().catch((error: HttpErrorResponse) => {
        return null;
      });
    }
    async deleteCarByOwner(id: number) {
      return this.http.delete<Car>(
        `http://localhost:8080/cars/by-owner/${id}`,
        httpOptions
      ).toPromise().catch((error: HttpErrorResponse) => {
        return null;
      });
    }
async editCar(id : number,manufacturer : string, model : string,
  doors : number, engine : string, ccm: number, ac : boolean){
    try {
      await this.http
        .put(
          `http://localhost:8080/cars/${id}`,
          {
            "manufacturer" : manufacturer,
            "model" : model,
            "doors" : doors,
            "engine" : engine,
            "ccm" : ccm,
            "ac" : ac
          },
          httpOptions
        )
        .toPromise();

    }catch (e) {
    console.log("hiba", e);
    return Promise.resolve(false);
    }
  }

}
