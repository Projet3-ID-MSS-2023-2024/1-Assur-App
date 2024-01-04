import { Component } from '@angular/core';
import {NgClass, NgOptimizedImage, Location} from "@angular/common";
import {Router, RouterLink} from "@angular/router";
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
  constructor(private location: Location,
              private authenticationService: AuthenticationService,
              private router: Router) {}

  active(path: string): boolean {
    return this.location.path(false).includes(path);
  }

  getRole(): string {
    return this.authenticationService.getUserRole();
  }

  logout(){
    this.authenticationService.logout();
    setTimeout(() => {
      this.router.navigate(['/']);
    }, 1000);
  }

  protected readonly Roles = Roles;
}
