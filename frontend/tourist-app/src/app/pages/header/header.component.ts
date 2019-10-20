import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/User/user.service';
import { LocalStorageService } from 'src/app/services/Local Storage/local-storage.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  private userService : UserService

  constructor(userService : UserService, private localStorageData : LocalStorageService) { 
    this.userService = userService

  }

  ngOnInit() {
    if (this.localStorageData.getToken() && this.localStorageData.getUserData()) {
      this.userService.setUser(this.localStorageData.getUserData());
      this.userService.setToken(this.localStorageData.getToken().replace(/"/g, ""));
    }
    
  }

}
