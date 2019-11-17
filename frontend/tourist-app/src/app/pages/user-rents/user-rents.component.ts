import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/User/user.service';
import { RentService } from 'src/app/services/Rent/rent.service';
import { Rent } from 'src/app/classes/Rent';
import { Router } from '@angular/router';
import { MotobikeService } from 'src/app/services/Motobike/motobike.service';
import { CarService } from 'src/app/services/Car/car.service';
import { FavouriteService } from 'src/app/services/Favourite/favourite.service';
import * as jsPDF from 'jspdf'
import { ReceiptService } from 'src/app/services/Receipt/receipt.service';
import { User } from 'src/app/classes/User';
import { formatDate } from '@angular/common';
import { StarRatingComponent } from 'ng-starrating';

@Component({
  selector: 'app-user-rents',
  templateUrl: './user-rents.component.html',
  styles: [`
    .star {
      font-size: 1.5rem;
      color: #b0c4de;
    }
    .filled {
      color: #1e90ff;
    }
    .bad {
      color: #deb0b0;
    }
    .filled.bad {
      color: #ff1e1e;
    }
  `]
})
export class UserRentsComponent implements OnInit {
  currentRate = 6;
  constructor(private router : Router,private userService : UserService, private rentService : RentService,
    private bikeService : MotobikeService, private carService : CarService, private favouriteService :FavouriteService, private receiptService : ReceiptService) {
  }
  rent : Rent 
  rents : Rent[]
  users : User[]
  months : Number[]
  async ngOnInit() {
    this.rents = await this.rentService.getRentsByUser(this.userService.user.id)
    this.users = await this.userService.getUsers()
    this.months = [31,30,31,30,31,30,31,31,30,31,30,31]
    console.log(this.rents)
  }
  returnRentedItem(id : number){
    this.rentService.returnRentedItem(id)
    window.location.reload();
   
    
  }
  createBill(id : number, rentID :number){
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
        var cost = (sum1 - sum) * 10
        console.log("The total cost: ", cost, "Ft")
        if(this.rent.car){
          this.receiptService.addReceipt(this.rent.car.id, 0, this.rent.start, this.rent.end, cost)
          this.downloadPdf(this.rent.user.username,this.rent.user.id, this.rent.start, this.rent.end, this.rent.car.manufacturer, this.rent.car.model, this.rent.car.owner.username, cost)
          this.rentService.setPayed(this.rent.id)
        }else if(this.rent.motobike){
          this.receiptService.addReceipt(0, this.rent.motobike.id, this.rent.start, this.rent.end, cost)
          this.downloadPdf(this.rent.user.username,this.rent.user.id, this.rent.start, this.rent.end, this.rent.motobike.manufacturer, this.rent.motobike.model, this.rent.motobike.owner.username, cost)
          this.rentService.setPayed(this.rent.id)
        }
        this.router.navigate(['home']);
      }
    }
  }

  downloadPdf(username : string, userID : number, start  : Date, end: Date, manufacturer : string, model : string, company : string, cost : number){ 
   
    const date = formatDate(Date.now(), "yyyy-MM-dd", "en-US");
    const doc = new jsPDF();
    var text = "The receipt is created for the user '" + username + "', user ID :" +userID+" \nfor renting the followin veichle : " 
    + manufacturer + " " + model +"\nfrom the company "+ "'" + company + "'" + " with the starting date of " 
    + start + "\nand with the ending date of " + end + ".\nThe final cost of the rent is: " + cost + ".\n\n" + "Date : "+ date
    doc.text(text, 20, 20);
    doc.save('Bill' + username + end.toString().substring(0,10)+'.pdf');
 }
 onRate($event:{newValue:number}, id: number) {
    var num = $event.newValue
    this.userService.edit(num, id)
  }
}