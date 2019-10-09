import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/User/user.service';
import { FormBuilder, FormGroup, Validators} from "@angular/forms";
import { Router } from '@angular/router';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm : FormGroup
  constructor(private fb : FormBuilder, private router : Router, private userService : UserService) { 
    this.loginForm= this.fb.group({
      username: ["", Validators.required],
	    password: ["", Validators.required],
    });
  }

  ngOnInit() {
  }

  login(){
    if(this.loginForm.invalid){
      console.log("hiba")
      return
    }

    this.userService.login(this.loginForm.value.username,
        this.loginForm.value.password).then(response =>{
          this.router.navigate(['/home'])
        })
    console.log(this.userService.isLoggedIn())
  }
}
