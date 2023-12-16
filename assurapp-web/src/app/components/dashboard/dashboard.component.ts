import { Component } from '@angular/core';
import {NgOptimizedImage} from "@angular/common";
import {RouterLink, RouterOutlet} from "@angular/router";

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    NgOptimizedImage,
    RouterOutlet,
    RouterLink
  ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {

}
