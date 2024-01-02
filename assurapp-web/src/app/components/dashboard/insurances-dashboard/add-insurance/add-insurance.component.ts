import { Component } from '@angular/core';
import {InsuranceType} from "../../../../enums/insurance-type";
import {InsuranceService} from "../../../../services/insurance.service";
import {FormBuilder, FormGroup, ReactiveFormsModule} from "@angular/forms";
import {Router} from "@angular/router";
import {UserService} from "../../../../services/user.service";
import {NgClass} from "@angular/common";

@Component({
  selector: 'app-add-insurance',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NgClass
  ],
  templateUrl: './add-insurance.component.html',
  styleUrl: './add-insurance.component.css'
})
export class AddInsuranceComponent {
  types:InsuranceType[] = Object.values(InsuranceType);
  insuranceForm: FormGroup;
  added: boolean = false;
  error : boolean = false;
  constructor(private insuranceService: InsuranceService,
              private userService: UserService,
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
    this.userService.getUserById(0).subscribe({
      next: data => {
        this.insuranceForm.value.insurer = data;
        this.insuranceService.addInsurance(this.insuranceForm.value).subscribe({
          next: data => {
            this.added = true;
            setTimeout(() => this.router.navigate(['/dashboard/insurances']), 5000)
          },
          error: err => {
            this.error = true
            setTimeout(() => this.error = false, 5000)
          }
        })
      },
      error: err => {
        this.error = true
        setTimeout(() => this.error = false, 5000)
      }
      })
  }
}
