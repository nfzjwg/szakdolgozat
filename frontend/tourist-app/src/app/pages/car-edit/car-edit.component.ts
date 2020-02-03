import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CarService } from 'src/app/services/Car/car.service';
import { UserService } from 'src/app/services/User/user.service';
import { Car } from 'src/app/classes/Car';

@Component({
  selector: 'app-car-edit',
  templateUrl: './car-edit.component.html',
  styleUrls: ['./car-edit.component.css']
})
export class CarEditComponent implements OnInit {
  car : Car
  editCarForm : FormGroup;
  manufacturer: string;
  model:string;
  doors: number;
  engine :string;
  ccm:number;
  ac:boolean;
  
  constructor(private fb: FormBuilder,private router: Router,private userService: UserService,private carService : CarService) {     
    this.editCarForm = this.fb.group({
      manufacturer : [""],
      model : [""],
      doors : [""],
      engine : [""],
      ccm : [""],
      ac : [""]
    });
  }

  ngOnInit() {}
  editCar(){
    if (this.editCarForm.invalid){
      console.log("hiba")
    }
    if(this.editCarForm.value.manufacturer == ""){
      this.manufacturer = this.carService.car.manufacturer
    }else{
      this.manufacturer = this.editCarForm.value.manufacturer
    }
    if(this.editCarForm.value.model == ""){
      this.model = this.carService.car.model
    }else{
      this.model = this.editCarForm.value.model
    }
    if(this.editCarForm.value.doors == ""){
      this.doors = this.carService.car.doors
    }else{
      this.doors = this.editCarForm.value.doors
    }
    if(this.editCarForm.value.engine == ""){
      this.engine = this.carService.car.engine
    }else{
      this.engine = this.editCarForm.value.engine
    }
    if(this.editCarForm.value.ccm == ""){
      this.ccm = this.carService.car.ccm
    }else{
      this.ccm = this.editCarForm.value.ccm
    }
    if(this.editCarForm.value.ac == ""){
      this.ac = this.carService.car.ac
    }else{
      if(this.editCarForm.value.ac == "Yes"){
        this.ac = true
      }else{
        this.ac = false
      }
    }
    this.carService.editCar(this.carService.car.id,this.manufacturer,
      this.model,this.doors,this.engine,this.ccm,this.ac)
      this.router.navigate(['cars']);
  }


  
}
