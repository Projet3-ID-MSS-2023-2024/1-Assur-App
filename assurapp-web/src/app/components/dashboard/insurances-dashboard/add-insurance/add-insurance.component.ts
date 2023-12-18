import { Component } from '@angular/core';
import {InsuranceType} from "../../../../enums/insurance-type";
import {InsuranceService} from "../../../../services/insurance.service";
import {FormBuilder, FormGroup, ReactiveFormsModule} from "@angular/forms";
import {Router} from "@angular/router";

@Component({
  selector: 'app-add-insurance',
  standalone: true,
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './add-insurance.component.html',
  styleUrl: './add-insurance.component.css'
})
export class AddInsuranceComponent {
  types:InsuranceType[] = Object.values(InsuranceType);
  insuranceForm: FormGroup;
  constructor(private insuranceService: InsuranceService,
              private formBuilder: FormBuilder,
              private router: Router) {
    this.insuranceForm = this.formBuilder.group({
      id: 0,
      name: '',
      type: '',
      coverageAmount: 10000.00,
      insurer: {

      },
      terms: this.formBuilder.array(
        [
          this.formBuilder.group({
            id: 0,
            name: '',
            description: ''
          }),
          this.formBuilder.group({
            id: 0,
            name: '',
            description: ''
          }),
          this.formBuilder.group({
            id: 0,
            name: '',
            description: ''
          })
        ]
      )
    })
  }

  onSubmit() {
    this.insuranceService.addInsurance(this.insuranceForm.value).subscribe({
      next: data => this.router.navigate(['/dashboard/insurances']),
      error: err => console.error(err)
    })
  }
}
