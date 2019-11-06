import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/User/user.service';

@Component({
  selector: 'app-profile-data',
  templateUrl: './profile-data.component.html',
  styleUrls: ['./profile-data.component.css']
})
export class ProfileDataComponent implements OnInit {

  constructor(private userService : UserService) { }

  ngOnInit() {
  }

}
