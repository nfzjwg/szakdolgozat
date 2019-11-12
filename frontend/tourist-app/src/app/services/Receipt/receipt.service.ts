import { Injectable } from '@angular/core';
import { UserService,httpOptions } from '../User/user.service';
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { Receipt } from 'src/app/classes/Receipt';
@Injectable({
  providedIn: 'root'
})
export class ReceiptService {
  constructor(
    private http : HttpClient, private userService : UserService) {}
  async getReceipt(id: number) {
		return this.http.get<Receipt>(
			`http://localhost:8080/receipts/${id}`,
			httpOptions
		).toPromise().catch((error: HttpErrorResponse) => {
			return null;
		});
	}

  async getReceipts(){
    return this.http.get<Receipt[]>(
      `http://localhost:8080/receipts`,      
      httpOptions
    ).toPromise().catch((error:HttpErrorResponse) => {
      return new Array<Receipt>()
    });
  }
  
  async getReceiptsByUser(userID : number){
    return this.http.get<Receipt[]>(
      `http://localhost:8080/receipts/by-user?user=${userID}`,      
      httpOptions
    ).toPromise().catch((error:HttpErrorResponse) => {
      return new Array<Receipt>()
    });
	}


  async addReceipt(carID : number, motobikeID : number, start : Date, end : Date, cost : number){
    return this.http.post(`http://localhost:8080/receipts/upload?user_id=${this.userService.user.id}&car_id=${carID}&motobike_id=${motobikeID}`,
      {
        "start" : start,
        "end" : end,
        "cost" : cost
      }, httpOptions).toPromise();
      
    }
    async editReceipt(id :number, cost : number) {
      await this.http.put(`http://localhost:8080/receipts/${id}`, 
      {
        "cost" : cost
      }, httpOptions).toPromise();
      return Promise.resolve(true);
    } catch (e) {
      console.log("hiba", e);
      return Promise.resolve(false);
    }
   
    
  }

