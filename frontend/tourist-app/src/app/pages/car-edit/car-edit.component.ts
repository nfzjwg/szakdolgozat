import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CarService } from 'src/app/services/Car/car.service';
import { UserService } from 'src/app/services/User/user.service';

@Component({
  selector: 'app-car-edit',
  templateUrl: './car-edit.component.html',
  styleUrls: ['./car-edit.component.css']
})
export class CarEditComponent implements OnInit {
  editForm : FormGroup;
  manufacturer: string;
  model:string;
  doors: number;
  engine :string;
  ccm:number;
  ac:boolean;
  
  constructor(private fb: FormBuilder,private router: Router,private userService: UserService,private carService : CarService) {     
    this.editForm = this.fb.group({
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

  
}
