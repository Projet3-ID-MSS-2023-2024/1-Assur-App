import { Component } from '@angular/core';
import {NgOptimizedImage} from "@angular/common";
import {RouterLink} from "@angular/router";
import {NavbarComponent} from "./navbar/navbar.component";
import {HeroComponent} from "./hero/hero.component";
import {ProductsComponent} from "./products/products.component";
import {InsurancesComponent} from "./insurances/insurances.component";
import {StatsComponent} from "./stats/stats.component";
import {FooterComponent} from "./footer/footer.component";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    NgOptimizedImage,
    RouterLink,
    NavbarComponent,
    HeroComponent,
    ProductsComponent,
    InsurancesComponent,
    StatsComponent,
    FooterComponent
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {

}
