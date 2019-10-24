import { Component, OnInit } from '@angular/core';
import { Motobike } from 'src/app/classes/Motobike';
import { UserService } from 'src/app/services/User/user.service';
import { MotobikeService } from 'src/app/services/Motobike/motobike.service';

@Component({
  selector: 'app-motorcycle',
  templateUrl: './motorcycle.component.html',
  styleUrls: ['./motorcycle.component.css']
})
export class MotorcycleComponent implements OnInit {
  motobikes : Motobike[]
  private userService :UserService
  constructor(private motobikeService : MotobikeService, userService :UserService) { 
    this.userService = userService
  }
  async ngOnInit() {
    this.motobikes = await this.motobikeService.getMotobikes()
    console.log(this.motobikes)
  }

}
