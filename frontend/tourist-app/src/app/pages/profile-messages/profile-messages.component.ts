import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/User/user.service';
import { MessageService } from 'src/app/services/Message/message.service';
import { Message } from 'src/app/classes/Message';
import { User } from 'src/app/classes/User';

@Component({
  selector: 'app-profile-messages',
  templateUrl: './profile-messages.component.html',
  styleUrls: ['./profile-messages.component.css']
})
export class ProfileMessagesComponent implements OnInit {
  messages : Message[]
  reciver : User
  private userService : UserService;
  private messageService : MessageService;
  constructor( userService : UserService,  messageService : MessageService) { 
    this.userService = userService;
    this.messageService = messageService;
  }
    
  async ngOnInit() {
    this.messages = await this.messageService.getMessage(this.userService.user.id);
    console.log(this.messages)
  }
  getSender(){
    return this.userService.user.username
  }
  async getReciver(id : number){
    this.reciver = await this.userService.getUser(id)
    console.log(this.reciver)
  }
}
