import { Component } from '@angular/core';
import {AuthenticationService} from "../../../../services/authentication.service";
import {Router, RouterLink} from "@angular/router";

@Component({
  selector: 'app-confirm-delete',
  standalone: true,
  imports: [
    RouterLink
  ],
  templateUrl: './confirm-delete.component.html',
  styleUrl: './confirm-delete.component.css'
})
export class ConfirmDeleteComponent {

  constructor(private authService:AuthenticationService, private route: Router) {
  }
  deleteAccount(){
    this.authService.deleteAccount(this.authService.getUserEmail()).subscribe(
      {
        next:()=>{
          this.authService.logout()
          this.route.navigate([''])
        }
      }
    )
  }
}
