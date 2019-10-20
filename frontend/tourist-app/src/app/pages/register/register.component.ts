import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/User/user.service';


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  registerForm: FormGroup;
  constructor(private fb: FormBuilder,private router: Router,private userService: UserService) {
    this.registerForm = this.fb.group({
      username: ["", Validators.required],
      email: ["", Validators.required],
      password: ["", Validators.required],
      role: ["", Validators.required]
    });
  }

  ngOnInit() {}
  register() {
    if (this.registerForm.invalid) {
      console.log("hiba");
      console.log(this.registerForm.value.username);
      return;
  	}
	console.log("register");
	
  this.userService.register(this.registerForm.value.username,this.registerForm.value.password,
      this.registerForm.value.email,this.registerForm.value.role).then((response) =>{
        if(response){
          this.router.navigate(['users/login']);
        }
	});
  }
}
