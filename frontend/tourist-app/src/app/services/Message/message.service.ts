import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient, HttpResponse, HttpErrorResponse } from "@angular/common/http";
import { UserService, httpOptions } from '../User/user.service';
import { Message } from 'src/app/classes/Message';
@Injectable({
  providedIn: 'root'
})
export class MessageService {
	id: number;
  constructor(private http : HttpClient, private userService : UserService) { }

  async getMessage(id: number) {
		return this.http.get<Message>(
			`http://localhost:8080/messages/by-user?sender=${id}`,
			httpOptions
		).toPromise().catch((error: HttpErrorResponse) => {
			return null;
		});
	}

	async sendMessage(sender : number, reciver : number, text : string){
   console.log(text);
	   return this.http.post(`http://localhost:8080/messages/add?sender=${sender}&reciver=${reciver}`,
		{
		  "text" : text
		}, httpOptions).toPromise();
	   }
}
