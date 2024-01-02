import {Component, OnInit} from '@angular/core';
import {NgIf, NgOptimizedImage} from "@angular/common";
import {Router, RouterLink} from "@angular/router";
import {AuthenticationService} from "../../../services/authentication.service";

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [
    NgOptimizedImage,
    RouterLink,
    NgIf
  ],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent implements OnInit{
  isLogged:boolean = false;
  constructor(private authService: AuthenticationService, private route: Router) {

  }

  ngOnInit(): void {
    this.isLogged = this.authService.isLogged();
  }

  logout(){
    this.authService.logout();
    location.reload()
  }

}
