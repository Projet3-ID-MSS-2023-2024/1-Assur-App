import {Component, OnInit} from '@angular/core';
import {NavbarComponent} from "../home/navbar/navbar.component";
import {RouterLink} from "@angular/router";
import {Insurance} from "../../interfaces/insurance";
import {InsuranceService} from "../../services/insurance.service";

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
export class InsurancesComponent implements OnInit {
  insurances: Insurance[] = [];

  constructor(private insuranceService: InsuranceService) {}

  ngOnInit() {
    this.insuranceService.getAllInsurances().subscribe({
      next: data => {
        this.insurances = data
      },
      error: err => console.error(err)
    })
  }
}
