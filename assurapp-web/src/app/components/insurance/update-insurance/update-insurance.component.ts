import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import {Insurance} from "../../../interfaces/insurance";

@Component({
  selector: 'app-update-insurance',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './update-insurance.component.html',
  styleUrl: './update-insurance.component.css'
})
export class UpdateInsuranceComponent {
  insurance!: Insurance;
}
