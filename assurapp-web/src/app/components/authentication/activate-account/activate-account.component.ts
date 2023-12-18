import {Component, OnInit} from '@angular/core';
import {RegisterService} from "../../../services/authentication/register.service";
import {ActivatedRoute, Router, RouterLink} from "@angular/router";
import {ActivateAccountService} from "../../../services/authentication/activate-account.service";
import {FormsModule} from "@angular/forms";
import {RegisterComponent} from "../register/register.component";
import {MessageService} from "primeng/api";
import {CommonModule} from "@angular/common";
import {MessageModule} from "primeng/message";
import {MessagesModule} from "primeng/messages";
import {ToastModule} from "primeng/toast";

@Component({
  selector: 'app-activate-account',
  standalone: true,
  imports: [
    RouterLink,
    FormsModule,
    CommonModule, MessageModule, MessagesModule, ToastModule
  ],
  templateUrl: './activate-account.component.html',
  styleUrl: './activate-account.component.css',
  providers: [MessageService]
})
export class ActivateAccountComponent implements OnInit{

  email: string = ""
  code: string=""
  constructor(private activateAccountService : ActivateAccountService, private route: Router, private activeRoute: ActivatedRoute, private messageService: MessageService) {
  }
  ngOnInit(): void {
    this.email = this.activeRoute.snapshot.paramMap.get('email') || ''
  }

  onSubmit(){
    this.activateAccountService.activateAccount(this.email, this.code).subscribe(
      {
        next:(data)=>{
          console.log(data)
          console.log("sucess")
          this.route.navigate(["/login"])
        },
        error:(error) =>{
          console.log(error);
        }
      }
    )
  }

  regenerate(){
    this.activateAccountService.regenerateCode(this.email).subscribe(
      {
        next:(data)=>{
          console.log(data)
          console.log("sucess")
        },
        error:(error) =>{
          console.log(error);
        }
      }
    )
  }
}
