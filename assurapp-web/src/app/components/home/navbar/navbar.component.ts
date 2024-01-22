import {Component, HostListener, Input, OnInit} from '@angular/core';
import {NgClass, NgIf, NgOptimizedImage} from "@angular/common";
import {Router, RouterLink} from "@angular/router";
import {AuthenticationService} from "../../../services/authentication.service";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [
    NgOptimizedImage,
    RouterLink,
    NgIf,
    NgClass
  ],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent implements OnInit{
  isLogged:boolean = false;
  timeBeforeLogout !: number;
  interval!: any;
  subscribe !: Subscription;
  visible: boolean = false;

  constructor(private authService: AuthenticationService, private route: Router) {}

  ngOnInit(): void {
    this.timeBeforeLogout = 1800;
    this.isLogged = this.authService.isLogged();
    this.startTimer()
  }

  logout(){
    this.authService.logout();
    clearInterval(this.interval)
    location.reload();
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
        this.logout()
      }
    }, 1000)
  }

  resetTimer(){
    this.timeBeforeLogout = 1800;
  }

  toggle() {
    this.visible = !this.visible;
  }
}
