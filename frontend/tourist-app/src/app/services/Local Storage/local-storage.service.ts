import { Injectable } from "@angular/core";
import { User } from 'src/app/classes/User';

@Injectable({
  providedIn: "root"
})
export class LocalStorageService {
  constructor() {}

  removeToken() {
    return localStorage.removeItem("token");
  }

  setToken(newToken: string) {
    return localStorage.setItem("token", newToken);
  }

  getToken() {
    return JSON.stringify(localStorage.getItem("token"));
  }

  getUserData(): User{
    return JSON.parse(localStorage.getItem("userData"));
  }

  setUserData(userData: User) {
    return localStorage.setItem("userData", JSON.stringify(userData));
  }

  removeUserData() {
    return localStorage.removeItem("userData");
  }
}
