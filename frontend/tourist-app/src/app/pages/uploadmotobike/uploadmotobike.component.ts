import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MotobikeService } from 'src/app/services/Motobike/motobike.service';
import { UserService } from 'src/app/services/User/user.service';
@Component({
  selector: 'app-uploadmotobike',
  templateUrl: './uploadmotobike.component.html',
  styleUrls: ['./uploadmotobike.component.css']
})
export class UploadmotobikeComponent implements OnInit {
  uploadMotobikeForm : FormGroup;
 
  constructor(private fb : FormBuilder, private router : Router,private motobikeService : MotobikeService, private userService: UserService) { 
    this.uploadMotobikeForm = this.fb.group({
      manufacturer : ["", Validators.required],
      model : ["", Validators.required],
      ccm : [""]
    });
  }

  ngOnInit() {
  }
  uploadMotobike(){
    if(this.uploadMotobikeForm.invalid){
      console.log("hiba");
      return;
    }
   
    this.motobikeService.addMotobike(this.uploadMotobikeForm.value.manufacturer,
      this.uploadMotobikeForm.value.model, 
      Number(this.uploadMotobikeForm.value.ccm),
     false, this.userService.user.id).then((response) =>{
        if(response){
          console.log("uploaded");
        }
      })
  }

}
