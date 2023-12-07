import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterLink} from "@angular/router";
import {InsuranceService} from "../../../services/insurance.service";
import {Observable} from "rxjs";
import {Insurance} from "../../../interfaces/insurance";
import {environment} from "../../../../environments/environment.development";

@Component({
  selector: 'app-add-insurance',
  standalone: true,
    imports: [CommonModule, RouterLink],
  templateUrl: './add-insurance.component.html',
  styleUrl: './add-insurance.component.css'
})
export class AddInsuranceComponent {

  insurance!: Insurance;

  constructor(private service: InsuranceService) {
  }

  addInsurance() {
    this.service.addInsurance(this.insurance).subscribe(data => {})
  }

}
