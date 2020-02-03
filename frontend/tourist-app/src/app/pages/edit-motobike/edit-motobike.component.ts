import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/User/user.service';
import { MotobikeService } from 'src/app/services/Motobike/motobike.service';
import { Motobike } from 'src/app/classes/Motobike';

@Component({
  selector: 'app-edit-motobike',
  templateUrl: './edit-motobike.component.html',
  styleUrls: ['./edit-motobike.component.css']
})
export class EditMotobikeComponent implements OnInit {
  bike : Motobike
  editBikeForm : FormGroup;
  manufacturer: string;
  model:string;
  ccm:number;

  constructor(private fb: FormBuilder,private router: Router,private userService: UserService,private bikeService : MotobikeService) {     
    this.editBikeForm = this.fb.group({
      manufacturer : [""],
      model : [""],
      ccm : [""],
    });
  }
  ngOnInit() {}

  editBike(){
    if (this.editBikeForm.invalid){
      console.log("hiba")
    }
    if(this.editBikeForm.value.manufacturer == ""){
      this.manufacturer = this.bikeService.bike.manufacturer
    }else{
      this.manufacturer = this.editBikeForm.value.manufacturer
    }
    if(this.editBikeForm.value.model == ""){
      this.model = this.bikeService.bike.model
    }else{
      this.model = this.editBikeForm.value.model
    }
    
    if(this.editBikeForm.value.ccm == ""){
      this.ccm = this.bikeService.bike.ccm
    }else{
      this.ccm = this.editBikeForm.value.ccm
    }
   
    
    this.bikeService.editBike(this.bikeService.bike.id,this.manufacturer,
      this.model,this.ccm)
      this.router.navigate(['motobikes']);
      
    }

}
