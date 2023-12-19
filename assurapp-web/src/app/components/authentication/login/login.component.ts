import {Component, OnInit} from '@angular/core';
import {CommonModule, DOCUMENT} from '@angular/common';
import {RegisterService} from "../../../services/authentication/register.service";
import {MessageService} from "primeng/api";
import {User} from "../../../interfaces/user";
import {LoginService} from "../../../services/authentication/login.service";
import {ToastModule} from "primeng/toast";
import {FormsModule} from "@angular/forms";
import {MessageModule} from "primeng/message";
import {MessagesModule} from "primeng/messages";
import {RouterLink} from "@angular/router";
import {NavbarComponent} from "../../home/navbar/navbar.component";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ToastModule, MessageModule, MessagesModule, FormsModule, RouterLink, NavbarComponent],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
  providers: [MessageService]
})
export class LoginComponent implements OnInit{
  user! : User;
  constructor(private loginService: LoginService, private messageService: MessageService) {
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

  login(){
    this.user.id = 0;
    this.loginService.login(this.user.email, this.user.password).subscribe({
      next:(data)=>{
        console.log(data);
        this.messageService.add({severity:'success', summary: 'Success', detail: 'User authenticated'})

      },
      error:(error) =>{
        console.log(error);
        this.messageService.add({severity:'error', summary: 'Error', detail: 'Error occured'})
      }
    })
  }
}
