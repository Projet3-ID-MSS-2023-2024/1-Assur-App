import {Component, HostListener, Input, OnInit} from '@angular/core';
import {NgIf, NgOptimizedImage} from "@angular/common";
import {Router, RouterLink} from "@angular/router";
import {AuthenticationService} from "../../../services/authentication.service";
import {Subscription} from "rxjs";

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
  timeBeforeLogout : number = 900;
  interval: number | undefined;
  subscribe !: Subscription;

  constructor(private authService: AuthenticationService, private route: Router) {
  }

  ngOnInit(): void {
    this.isLogged = this.authService.isLogged();
    this.startTimer()
  }

  logout(){
    this.authService.logout();
    location.reload()
  }


  @HostListener('window:keypress', ["$event"])
  @HostListener('window:beforeunload', ["$event"])
  @HostListener('window:mousemove', ["$event"])
  mouseMoveWatcher(event:Event){
    this.resetTimer()
  }

  startTimer(){
    this.interval = setInterval(()=>{
      if(this.timeBeforeLogout > 0){
        this.timeBeforeLogout--;
      } else if(this.timeBeforeLogout == 0 && this.isLogged){
        this.authService.logout()
        location.reload()
      }
    }, 1000)
  }

  resetTimer(){
    this.timeBeforeLogout = 900;
  }

}
