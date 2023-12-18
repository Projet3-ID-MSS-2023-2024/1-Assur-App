import { Component } from '@angular/core';
import {NavbarComponent} from "../home/navbar/navbar.component";
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-insurances',
  standalone: true,
  imports: [
    NavbarComponent,
    RouterLink
  ],
  templateUrl: './insurances.component.html',
  styleUrl: './insurances.component.css'
})
export class InsurancesComponent {

}
