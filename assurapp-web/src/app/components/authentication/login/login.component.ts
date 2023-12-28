import {Component, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {User} from "../../../interfaces/user";
import {LoginService} from "../../../services/authentication/login.service";
import {ToastModule} from "primeng/toast";
import {FormsModule} from "@angular/forms";
import {MessageModule} from "primeng/message";
import {MessagesModule} from "primeng/messages";
import {Router, RouterLink} from "@angular/router";
import {NavbarComponent} from "../../home/navbar/navbar.component";
import {TokenService} from "../../../services/token.service";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ToastModule, MessageModule, MessagesModule, FormsModule, RouterLink, NavbarComponent],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent implements OnInit{
  user! : User;
  constructor(private loginService: LoginService, private tokenService: TokenService, private router : Router) {
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
    this.loginService.login(this.user.email, this.user.password).subscribe({
      next:(data)=>{
        //console.log(data);
        this.tokenService.saveToken(data.bearer)

        //this.router.navigate([''])
      },
      error:(error) =>{
        console.log(error);
      }
    })
  }

  test(){
    console.log(this.tokenService.getUserRole())
    console.log("-----------")
    const data = JSON.parse(atob("eyJzdWIiOiJhbGljZS5zbWl0aEBleGFtcGxlLmNvbSIsImV4cCI6MTcwMzc1MTU4Nywicm9sZSI6eyJpZCI6MSwibGFiZWwiOiJDTElFTlQifSwiaWQiOjEsIm5hbWUiOiJhIn0"))

    console.log(data)
    console.log("-----------")
    console.log(data.sub)
  }
}
