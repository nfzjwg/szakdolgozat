import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/User/user.service';
import { MessageService } from 'src/app/services/Message/message.service';

@Component({
  selector: 'app-message-sender-form',
  templateUrl: './message-sender-form.component.html',
  styleUrls: ['./message-sender-form.component.css']
})
export class MessageSenderFormComponent implements OnInit {
  messageForm : FormGroup;
  constructor(private fb : FormBuilder, private router : Router,
    private userService : UserService, private messageService : MessageService) { 
      this.messageForm = this.fb.group({
        text : ["", Validators.required]
      });
    }

  ngOnInit() {
  }
  sendMessage(){
    if(this.messageForm.invalid){
      console.log("hiba");
      return;
    }
    console.log(this.messageService.id)
    this.messageService.sendMessage(this.userService.user.id,this.messageService.id,
      this.messageForm.value.text
      ).then((response) =>{
        if(response){
          console.log("Message sent");
        }
      })
      this.router.navigate(['home']);
  }
}
