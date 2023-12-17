import { Component } from '@angular/core';
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-insurances',
  standalone: true,
  imports: [
    RouterLink
  ],
  templateUrl: './insurances.component.html',
  styleUrl: './insurances.component.css'
})
export class InsurancesComponent {

}
