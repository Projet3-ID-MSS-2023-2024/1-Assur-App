import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-cart-xl',
  standalone: true,
    imports: [CommonModule, RouterLink],
  templateUrl: './cart-xl.component.html',
  styleUrl: './cart-xl.component.css'
})
export class CartXlComponent {

}
