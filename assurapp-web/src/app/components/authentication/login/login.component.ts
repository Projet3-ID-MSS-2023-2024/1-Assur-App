import {Component, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {User} from "../../../interfaces/user";
import {ToastModule} from "primeng/toast";
import {FormsModule} from "@angular/forms";
import {MessageModule} from "primeng/message";
import {MessagesModule} from "primeng/messages";
import {Router, RouterLink} from "@angular/router";
import {NavbarComponent} from "../../home/navbar/navbar.component";
import {AuthenticationService} from "../../../services/authentication.service";
import {PopupService} from "../../../services/popup.service";
import {PopupType} from "../../../enums/popup-type";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ToastModule, MessageModule, MessagesModule, FormsModule, RouterLink, NavbarComponent],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent implements OnInit{

  user! : User;
  constructor(private authService: AuthenticationService, private router : Router, private popService :PopupService) {
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

  onSubmit(){
    this.user.id = 0;
    this.authService.login(this.user.email, this.user.password).subscribe({
      next:(data)=>{
        this.authService.saveToken(data.bearer)
        this.router.navigate(['/dashboard'])
      },
      error:(error) =>{
        this.popService.show("Invalid data in fields", PopupType.ERROR)
      }
    })
  }
}
