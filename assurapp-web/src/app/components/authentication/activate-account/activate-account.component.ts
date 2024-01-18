import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router, RouterLink} from "@angular/router";
import {CommonModule} from "@angular/common";
import {MessageModule} from "primeng/message";
import {MessagesModule} from "primeng/messages";
import {ToastModule} from "primeng/toast";
import {FormsModule} from "@angular/forms";
import {AuthenticationService} from "../../../services/authentication.service";
import {DataService} from "../../../services/data.service";
import {PopupService} from "../../../services/popup.service";
import {PopupType} from "../../../enums/popup-type";

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
  constructor(private authService : AuthenticationService,
              private route: Router,
              private activeRoute: ActivatedRoute,
              private dataService: DataService,
              private popService: PopupService) {
  }
  ngOnInit(): void {
    this.email = this.dataService.getSharedData();
    if (!this.email){
      this.route.navigate([""])
    }
  }

  onSubmit(){
    this.authService.activateAccount(this.email, this.code).subscribe(
      {
        next:(data)=>{
          this.route.navigate(["/login"])
        },
        error:(error) =>{
          this.popService.show("Wrong activation code", PopupType.ERROR)
        }
      }
    )
  }

  regenerate(){
    this.authService.regenerateCode(this.email).subscribe(
      {
        next:(data)=>{
          this.popService.show("New code sent", PopupType.SUCCESS)
        },
        error:(error) =>{
          this.popService.show("Unknown error", PopupType.ERROR)
        }
      }
    )
  }
}
