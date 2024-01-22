import {Component, HostListener, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterLink, RouterOutlet} from '@angular/router';
import {HomeComponent} from "./components/home/home.component";
import {AuthenticationService} from "./services/authentication.service";
import {PopupComponent} from "./components/popup/popup.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink, HomeComponent, PopupComponent],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent{

  constructor(private authService: AuthenticationService) {
  }

  title = 'Assur-App';
  logged: boolean = this.authService.isLogged();
}
