import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CarService } from 'src/app/services/Car/car.service';
import { UserService } from 'src/app/services/User/user.service';

@Component({
  selector: 'app-uploadcar',
  templateUrl: './uploadcar.component.html',
  styleUrls: ['./uploadcar.component.css']
})
export class UploadcarComponent implements OnInit {
  uploadCarForm : FormGroup;
  constructor(private fb : FormBuilder, private router : Router,private carService : CarService, private userService: UserService) { 
    this.uploadCarForm = this.fb.group({
      manufacturer : ["", Validators.required],
      model : ["", Validators.required],
      doors : [""],
      engine : [""],
      ccm : [""],
      ac : [""]
    });
  }

  ngOnInit() {
  }
  uploadCar(){
    if(this.uploadCarForm.invalid){
      console.log("hiba");
      return;
    }
    console.log(this.uploadCarForm.value.ac)
    this.carService.addCar(this.uploadCarForm.value.manufacturer,
      this.uploadCarForm.value.model, Number(this.uploadCarForm.value.doors), 
      this.uploadCarForm.value.engine, Number(this.uploadCarForm.value.ccm),
      this.uploadCarForm.value.ac, false, this.userService.user.id).then((response) =>{
        if(response){
          console.log("uploaded");
        }
      })
  }

}
