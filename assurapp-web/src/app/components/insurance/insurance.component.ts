import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterLink} from "@angular/router";
import {Insurance} from "../../interfaces/insurance";
import {InsuranceService} from "../../services/insurance.service";

@Component({
  selector: 'app-insurance',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './insurance.component.html',
  styleUrl: './insurance.component.css'
})
export class InsuranceComponent {
  insurances: Insurance[] = [
    {
      id: 1,
      name: "random",
    },
    {
      id: 2,
      name: "random",
    }
  ];

  constructor(private service: InsuranceService) {}

  getAllInsurances(): void {
    this.service.getAllInsurances().subscribe(data => this.insurances = data);
  }

  deleteInsurance(id: number): void {
    this.service.deleteInsurance(id).subscribe(() => true);
  }
}
