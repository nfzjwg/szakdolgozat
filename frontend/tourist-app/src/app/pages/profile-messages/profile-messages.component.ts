import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/User/user.service';
import { MessageService } from 'src/app/services/Message/message.service';
import { Message } from 'src/app/classes/Message';
import { User } from 'src/app/classes/User';
import { Router } from '@angular/router';

@Component({
  selector: 'app-profile-messages',
  templateUrl: './profile-messages.component.html',
  styleUrls: ['./profile-messages.component.css']
})
export class ProfileMessagesComponent implements OnInit {
  messagesSent : Message[]
  messagesRecived : Message[]
  recivers : User[]
  senders : User[]
  reciver : User
  sender : User
  
  private userService : UserService;
  private messageService : MessageService;
  constructor(private router : Router, userService : UserService,  messageService : MessageService) { 
    this.userService = userService;
    this.messageService = messageService;
    this.recivers = [];
    this.senders = [];
  }
    
  async ngOnInit() {
  
    this.messagesSent = await this.messageService.getMessageBySender(this.userService.user.id);
    console.log(this.messagesSent)
    this.messagesRecived = await this.messageService.getMessageByReciver(this.userService.user.id);
    for( var m of this.messagesSent){
      this.reciver  = await this.userService.getUser(m.reciver)
      this.recivers.push(this.reciver)
    }
    for( var m of this.messagesRecived){
      this.sender  = await this.userService.getUser(m.sender)
      this.senders.push(this.sender)
    }
    
    console.log("recivers " +this.recivers)
    console.log("senders " +this.senders)
  }

  sendMessage(id : number){
    this.messageService.id = id;
    console.log("Send message here."+this.messageService.id)
    this.router.navigate(['sendMessage']);
  }
  
}
