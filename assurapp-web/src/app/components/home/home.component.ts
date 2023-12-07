import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterLink} from "@angular/router";
import {FooterComponent} from "./footer/footer.component";
import {ReactiveFormsModule} from "@angular/forms";
import {ItemComponent} from "./item/item.component";
import {CardComponent} from "./card/card.component";
import {CartXlComponent} from "./cart-xl/cart-xl.component";
import {ContactFormComponent} from "./contact-form/contact-form.component";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, RouterLink, FooterComponent, ReactiveFormsModule, ItemComponent, CardComponent, CartXlComponent, ContactFormComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {

}
