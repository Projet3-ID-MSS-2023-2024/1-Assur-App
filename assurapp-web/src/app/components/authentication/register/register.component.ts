import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {User} from "../../../interfaces/user";
import {RegisterService} from "../../../services/authentication/register.service";
import {FormsModule} from "@angular/forms";
import {MessageModule} from "primeng/message";
import {MessagesModule} from "primeng/messages";
import {MessageService} from "primeng/api";
import {ToastModule} from "primeng/toast";

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, MessageModule, MessagesModule, ToastModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css',
  providers: [MessageService]
})
export class RegisterComponent implements OnInit{
  user!: User;
  constructor(private registerService: RegisterService, private messageService: MessageService) {
  }

  ngOnInit(): void {
    this.user = {
      id:0,
      name:'',
      lastname:'',
      email:'',
      password:'',
    }
  }

  register(){
    this.user.id = 0;
    this.registerService.register(this.user).subscribe({
      next:(data)=>{
        console.log(data);
        this.messageService.add({severity:'success', summary: 'Success', detail: 'User added'})
      },
      error:(error) =>{
        console.log(error);
        this.messageService.add({severity:'error', summary: 'Error', detail: 'Error occured'})
      }

    })
  }



}
