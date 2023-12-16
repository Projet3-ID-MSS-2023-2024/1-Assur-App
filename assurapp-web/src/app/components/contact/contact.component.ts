import { Component } from '@angular/core';
import {NavbarComponent} from "../home/navbar/navbar.component";

@Component({
  selector: 'app-contact',
  standalone: true,
  imports: [
    NavbarComponent
  ],
  templateUrl: './contact.component.html',
  styleUrl: './contact.component.css'
})
export class ContactComponent {

}
