import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router, RouterLink} from "@angular/router";
import {ActivateAccountService} from "../../../services/authentication/activate-account.service";
import {CommonModule} from "@angular/common";
import {MessageModule} from "primeng/message";
import {MessagesModule} from "primeng/messages";
import {ToastModule} from "primeng/toast";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-activate-account',
  standalone: true,
  imports: [
    RouterLink,
    CommonModule, MessageModule, MessagesModule, ToastModule, FormsModule
  ],
  templateUrl: './activate-account.component.html',
  styleUrl: './activate-account.component.css',
})
export class ActivateAccountComponent implements OnInit{

  email: string = ""
  code: string=""
  constructor(private activateAccountService : ActivateAccountService, private route: Router, private activeRoute: ActivatedRoute) {
  }
  ngOnInit(): void {
    this.email = this.activeRoute.snapshot.paramMap.get('email') || ''
  }

  onSubmit(){
    console.log(this.email)
     console.log(this.code)
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

  test(){
    console.log(this.code)
  }
}
