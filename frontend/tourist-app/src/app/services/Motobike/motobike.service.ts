import { Injectable } from '@angular/core';
import { Motobike } from 'src/app/classes/Motobike';
import { UserService, httpOptions } from '../User/user.service';
import { HttpHeaders, HttpClient, HttpResponse, HttpErrorResponse } from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class MotobikeService {
  userService : UserService
  //private url = "http://localhost:8080/";
  constructor(
    private http: HttpClient
  ) { }
  async getMotobike(id: number) {
		return this.http.get<Motobike>(
			`http://localhost:8080/motobikes/${id}`,
			httpOptions
		).toPromise().catch((error: HttpErrorResponse) => {
			return null;
		});
	}

  async getMotobikes(){
    return this.http.get<Motobike[]>(
      `http://localhost:8080/motobikes`,      
      httpOptions
    ).toPromise().catch((error:HttpErrorResponse) => {
      return new Array<Motobike>()
    });
  }
  
  async getMotobikeByUser(userID : number){
    return this.http.get<Motobike[]>(
      `http://localhost:8080/motobikes/by-user?owner=${userID}`,      
      httpOptions
    ).toPromise().catch((error:HttpErrorResponse) => {
      return new Array<Motobike>()
    });
  }
  


  async addMotobike(manufacturer : string, model : string,
    ccm: number, rented  : boolean,  owner : number){
    console.log(manufacturer);
    return this.http.post(`http://localhost:8080/motobikes/upload?owner=`+owner,
      {
        "manufacturer" : manufacturer,
        "model" : model,
        "ccm" : ccm,
        "rented" : rented
      }, httpOptions).toPromise();
    }

    async deleteMotobike(id: number) {
      return this.http.delete<Motobike>(
        `http://localhost:8080/motobikes/${id}`,
        httpOptions
      ).toPromise().catch((error: HttpErrorResponse) => {
        return null;
      });
    }
    async deleteMotobikeByOwner(id: number) {
      return this.http.delete<Motobike>(
        `http://localhost:8080/motobikes/by-owner/${id}`,
        httpOptions
      ).toPromise().catch((error: HttpErrorResponse) => {
        return null;
      });
    }
}

