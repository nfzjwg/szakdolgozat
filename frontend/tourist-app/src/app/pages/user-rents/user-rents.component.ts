import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/User/user.service';
import { RentService } from 'src/app/services/Rent/rent.service';
import { Rent } from 'src/app/classes/Rent';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-rents',
  templateUrl: './user-rents.component.html',
  styleUrls: ['./user-rents.component.css']
})
export class UserRentsComponent implements OnInit {
  constructor(private router : Router,private userService : UserService, private rentService : RentService) {
  }
  rent : Rent 
  rents : Rent[]
  months : Number[]
  async ngOnInit() {
    this.rents = await this.rentService.getRentsByUser(this.userService.user.id)
    this.months = [31,30,31,30,31,30,31,31,30,31,30,31]
    console.log(this.rents)
  }
  returnRentedItem(id : number){
    this.rentService.returnRentedItem(id)
    window.location.reload();
   
    
  }
  createBill(id : number){
    for( var i in this.rents){
      if(id == Number(i)){
        this.rent = this.rents[i]
        var startMonth = Number(this.rent.start.toString().substring(5,7))
        var startDay = Number(this.rent.start.toString().substring(8,10))
        var startHour = Number(this.rent.start.toString().substring(11,13))
        var startMinutes = Number(this.rent.start.toString().substring(14,16))
//Now the return date
        var endMonth = Number(this.rent.end.toString().substring(5,7))
        var endDay = Number(this.rent.end.toString().substring(8,10))
        var endHour = Number(this.rent.end.toString().substring(11,13))
        var endMinutes = Number(this.rent.end.toString().substring(14,16))
        let monthPart = 0
      /*  if((endMonth- startMonth) != 0){
          monthPart=  Number(this.months[startMonth-1]) * 1440
        }
        var result = endMinutes - startMinutes + (endHour - startHour) * 60 +
          (endDay - startDay) * 1440 + monthPart
        console.log("the result is: ", result)*/
        var sum = 0
        for(let i = 0; i < startMonth; i++){
          sum+=Number(this.months[i])
        } 
        sum = sum * 1440 + startDay * 1440 + startHour * 60 + startMinutes
        var sum1 = 0
        for(let i = 0; i < endMonth; i++){
          sum1+=Number(this.months[i])
        } 
        sum1 = sum1 * 1440 + endDay * 1440 + endHour * 60 + endMinutes
        console.log("The total cost: ",(sum1 - sum) * 10, "Ft")
        
      }
    }
   
  }
}