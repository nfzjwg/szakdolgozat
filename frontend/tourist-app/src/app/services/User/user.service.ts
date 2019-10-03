import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient, HttpResponse } from "@angular/common/http";
import { User } from 'src/app/classes/User';
import { LocalStorageService } from '../Local Storage/local-storage.service';

export const httpOptions = {
  headers: new HttpHeaders({
    "Content-Type": "application/json",
    "Authorization": ""
  })
};


@Injectable({
  providedIn: 'root'
})
export class UserService {
  token : string = null;
  user : User = null;
  private url = "http://localhost:8080/users";
  constructor(
    private http: HttpClient,
    private localStorage: LocalStorageService
  ){}

  async register(username : string, password : string, email : string){
    try{ 
      await this.http.post(`${this.url}/register`, 
      {
        username : username,
        password: password,
        email: email
      }, httpOptions).toPromise();
    return Promise.resolve(true);
    } catch (e) {
      console.log("error", e);
      return Promise.resolve(false);
    }
  }
  async login(username: string, password: string): Promise<boolean> {
    const token = btoa(`${username}:${password}`);
    httpOptions.headers = httpOptions.headers.set(
      "Authorization",
      `Basic ${token}`
    );

    try{
      const user = await this.http.post<User>(`${this.url}/login`, {}, httpOptions)
      .toPromise()
      this.user= user;
      this.localStorage.setUserData(user);
      this.localStorage.setToken(token);
      return Promise.resolve(true);
    }catch (e) {
      console.log("error", e);
      return Promise.resolve(false);
    }
  }
  logout(){
    this.user= null;
    this.localStorage.removeToken();
    this.localStorage.removeUserData();

  }
  isLoggedIn(){
    return this.user != null;
  }
  getLoggedInUser(){
    return this.user;
  }

}
