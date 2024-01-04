import { Component } from '@angular/core';
import {NavbarComponent} from "../../home/navbar/navbar.component";

@Component({
  selector: 'app-privacy-policy',
  standalone: true,
  imports: [
    NavbarComponent
  ],
  templateUrl: './privacy-policy.component.html',
  styleUrl: './privacy-policy.component.css'
})
export class PrivacyPolicyComponent {

}
