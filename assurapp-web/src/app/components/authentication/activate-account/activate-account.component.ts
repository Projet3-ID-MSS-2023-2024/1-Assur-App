import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router, RouterLink} from "@angular/router";
import {CommonModule} from "@angular/common";
import {MessageModule} from "primeng/message";
import {MessagesModule} from "primeng/messages";
import {ToastModule} from "primeng/toast";
import {FormsModule} from "@angular/forms";
import {AuthenticationService} from "../../../services/authentication.service";
import {DataService} from "../../../services/data.service";

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
  constructor(private authService : AuthenticationService, private route: Router, private activeRoute: ActivatedRoute, private dataService: DataService) {
  }
  ngOnInit(): void {
    this.email = this.dataService.getSharedData();
    if (!this.email){
      this.route.navigate([""])
    }
  }

  onSubmit(){
    console.log(this.email)
     console.log(this.code)
    this.authService.activateAccount(this.email, this.code).subscribe(
      {
        next:(data)=>{
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
    this.authService.regenerateCode(this.email).subscribe(
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
