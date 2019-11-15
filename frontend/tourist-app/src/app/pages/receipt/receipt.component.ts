import { Component, OnInit } from '@angular/core';
import { formatDate } from '@angular/common';
import { Receipt } from 'src/app/classes/Receipt';
import { UserService } from 'src/app/services/User/user.service';
import { Router } from '@angular/router';
import * as jsPDF from 'jspdf'
import { ReceiptService } from 'src/app/services/Receipt/receipt.service';

@Component({
  selector: 'app-receipt',
  templateUrl: './receipt.component.html',
  styleUrls: ['./receipt.component.css']
})
export class ReceiptComponent implements OnInit {

  receipts : Receipt[]
  private userService : UserService
  private receiptService : ReceiptService
  constructor( private router : Router ,receiptService  : ReceiptService,  userService : UserService) {
    this.receiptService = receiptService;
    this.userService = userService;
   }

  async ngOnInit() {
    this.receipts = await this.receiptService.getReceipts()
    console.log(this.receipts)
  }
  createReceipt(id : number){
    for( var r of this.receipts){
      if (r.id == id){
        if(r.car){
          this.downloadPdf(r.user.username, r.user.id, r.start, r.end, r.car.manufacturer, r.car.model, r.car.owner.username, r.cost)
        }else if(r.motobike){
          this.downloadPdf(r.user.username,r.user.id, r.start, r.end, r.motobike.manufacturer, r.motobike.model, r.motobike.owner.username, r.cost)
        }
      }
    }

  }

  downloadPdf(username : string, userID : number,start  : Date, end: Date, manufacturer : string, model : string, company : string, cost : number){ 
   
    const date = formatDate(Date.now(), "yyyy-MM-dd", "en-US");
    const doc = new jsPDF();
    var text = "The receipt is created for the user '" + username + "', user ID: "+ userID+" \nfor renting the followin veichle : " 
    + manufacturer + " " + model +"\nfrom the company "+ "'" + company + "'" + " with the starting date of " 
    + start + "\nand with the ending date of " + end + ".\nThe final cost of the rent is: " + cost + ".\n\n" + "Date : "+ date
    doc.text(text, 20, 20);
    doc.save('Bill' + username + end.toString().substring(0,10)+'.pdf');
 }
}
