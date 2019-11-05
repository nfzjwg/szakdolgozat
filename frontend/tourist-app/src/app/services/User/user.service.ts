import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient, HttpResponse, HttpErrorResponse } from "@angular/common/http";
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

  async register(username : string, password : string, email : string, role: string){
    console.log(username, password,role,email);
    
    try{
      await this.http.post(`${this.url}/register`,
      {
        "username" : username,
        "password": password,
        "email": email,
        "role" : role
        
      }, httpOptions).toPromise();
      console.log("ok")
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
    try {
      const user = await this.http
        .post<User>(`${this.url}/login`, {}, httpOptions)
        .toPromise();
        console.log("passed");
        this.user = user;
        console.log(this.isLoggedIn())
      this.localStorage.setUserData(user);
      this.localStorage.setToken(token);
      console.log("user set")
      return Promise.resolve(true);
    } catch (e) {
      console.log("hiba", e);
      return Promise.resolve(false);
    }
  }

  logout(){
    this.user= null;
    this.localStorage.removeToken();
    this.localStorage.removeUserData();
    console.log("log out", this.isLoggedIn())
  }
  isLoggedIn(){
    return this.user != null;
  }
  getLoggedInUser(){
    return this.user;
  }
   setUser(user: User) {
  	this.user = <any>{};
	  this.user = user;
  }
  
  setToken(token: string){
    this.token = token;
    httpOptions.headers = httpOptions.headers.set(
      "Authorization",
      `Basic ${token}`
    );
  }
   async getUsers(){
    return this.http.get<User[]>(
      `http://localhost:8080/users`,      
      httpOptions
    ).toPromise().catch((error:HttpErrorResponse) => {
      return new Array<User>()
    });
  }
  isAdmin(){
    return this.user.role == "ROLE_ADMIN";
  }
  isCompany(){
    return this.user.role == "ROLE_COMPANY";
  }
  isGuest(){
    return this.user.role == "ROLE_GUEST";
  }


}
