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
  users : User[]
  reciver : User
  
  private userService : UserService;
  private messageService : MessageService;
  constructor( userService : UserService,  messageService : MessageService) { 
    this.userService = userService;
    this.messageService = messageService;
    this.users = [];
  }
    
  async ngOnInit() {
  
    this.messages = await this.messageService.getMessage(this.userService.user.id);
    console.log(this.messages)
    for( var m of this.messages){
      this.reciver  = await this.userService.getUser(m.reciver)
      this.users.push(this.reciver)
    }
    
    console.log(this.users)
  }
  getSender(){
    return this.userService.user.username
  }
  getReciver(id : number){
    console.log(this.users[id])
   return this.users[id]
  }
}
