import { Component } from '@angular/core';
import {NgClass, NgOptimizedImage, Location} from "@angular/common";
import {RouterLink} from "@angular/router";
import {AuthenticationService} from "../../../services/authentication.service";
import {InsuranceType} from "../../../enums/insurance-type";
import {Roles} from "../../../enums/roles";

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [
    NgOptimizedImage,
    RouterLink,
    NgClass
  ],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.css'
})
export class SidebarComponent {
  constructor(private authService: AuthenticationService) {
  }

  logout(){
    this.authService.logout()
  }

  constructor(private location: Location,
              private authenticationService: AuthenticationService) {}

  active(path: string): boolean {
    return this.location.path(false).includes(path);
  }

  getRole(): string {
    return this.authenticationService.getUserRole();
  }

  protected readonly Roles = Roles;
}
