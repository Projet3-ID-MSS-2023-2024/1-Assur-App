import {Component} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {Router, RouterLink} from "@angular/router";
import {AuthenticationService} from "../../../services/authentication.service";
import {PopupService} from "../../../services/popup.service";
import {PopupType} from "../../../enums/popup-type";

@Component({
  selector: 'app-forgot-password',
  standalone: true,
    imports: [
        FormsModule,
        ReactiveFormsModule,
        RouterLink
    ],
  templateUrl: './forgot-password.component.html',
  styleUrl: './forgot-password.component.css'
})
export class ForgotPasswordComponent {
  mail:string = "";
  return:string = "";
  constructor(private authService: AuthenticationService, private route: Router, private popService: PopupService) {
  }
  sendMail(){
    this.authService.sendForgotPasswordMail(this.mail).subscribe({
      next: () =>{
        this.route.navigate(["./mail"])
      },
      error: () => this.popService.show( "This mail address may not exist please check your input, if that persist contact us at support@assurapp.com",PopupType.ERROR)
      }
    )
  }
}
