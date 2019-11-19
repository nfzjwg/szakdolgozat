import { Component, OnInit } from '@angular/core';
import { Rent } from 'src/app/classes/Rent';
import { UserService } from 'src/app/services/User/user.service';
import { RentService } from 'src/app/services/Rent/rent.service';
import { Router } from '@angular/router';
import { formatDate } from '@angular/common';
import * as jsPDF from 'jspdf'
import { ReceiptService } from 'src/app/services/Receipt/receipt.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-rental',
  templateUrl: './rental.component.html',
  styleUrls: ['./rental.component.css']
})
export class RentalComponent implements OnInit {
  rents : Rent[]
  rent : Rent 
  months : Number[]
  private userService : UserService
  private rentService : RentService
  constructor( private router : Router ,rentService : RentService,  userService : UserService, 
    private receiptService : ReceiptService,private toastrService : ToastrService) {
    this.rentService = rentService;
    this.userService = userService;
   }

  async ngOnInit() {
    this.rents = await this.rentService.getRents();
    this.months = [31,30,31,30,31,30,31,31,30,31,30,31]
    console.log(this.rents)
  }
  returnRentedItem(id : number){
    this.rentService.returnRentedItem(id)
    this.router.navigate(['home']);
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
          this.receiptService.addReceipt(this.rent.car.owner.id,this.rent.car.id, 0, this.rent.start, this.rent.end, cost)
          this.downloadPdf(this.rent.user.username,this.rent.user.id, this.rent.start, this.rent.end, this.rent.car.manufacturer, this.rent.car.model, this.rent.car.owner.username, cost)
          this.rentService.setPayed(this.rent.id)
        }else if(this.rent.motobike){
          this.receiptService.addReceipt(this.rent.motobike.owner.id,0, this.rent.motobike.id, this.rent.start, this.rent.end, cost)
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
    console.log( "val" ,num)
    console.log( "id" ,id)
    this.userService.edit(num, id).then((response) =>{
      if(response){
        this.toastrService.success("Success!", "Rating")
      }else{
        this.toastrService.error("Error!","Rating")
      }
    })
    console.log("updated")
  }
}

