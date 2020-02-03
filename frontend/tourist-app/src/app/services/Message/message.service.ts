import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient, HttpErrorResponse } from "@angular/common/http";
import { UserService, httpOptions } from '../User/user.service';
import { Message } from 'src/app/classes/Message';
@Injectable({
  providedIn: 'root'
})
export class MessageService {
	id: number;
  constructor(private http : HttpClient, private userService : UserService) { }

  async getMessageBySender(id: number) {
		return this.http.get<Message>(
			`http://localhost:8080/messages/by-user?sender=${id}`,
			httpOptions
		).toPromise().catch((error: HttpErrorResponse) => {
			return null;
		});
	}

	async getMessageByReciver(id: number) {
		return this.http.get<Message>(
			`http://localhost:8080/messages/by-reciver?reciver=${id}`,
			httpOptions
		).toPromise().catch((error: HttpErrorResponse) => {
			return null;
		});
	}

	async sendMessage(sender : number, reciver : number, text : string){
	   return this.http.post(`http://localhost:8080/messages/add?sender=${sender}&reciver=${reciver}`,
		{
		  "text" : text
		}, httpOptions).toPromise();
	   }

	   async deleteMessageBySender(id: number) {
		return this.http.delete<Message>(
		  `http://localhost:8080/messages/by-sender/${id}`,
		  httpOptions
		).toPromise().catch((error: HttpErrorResponse) => {
		  return null;
		});
	  }
}
