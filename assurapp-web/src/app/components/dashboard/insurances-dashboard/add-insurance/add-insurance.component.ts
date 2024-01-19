import { Component } from '@angular/core';
import {InsuranceType} from "../../../../enums/insurance-type";
import {InsuranceService} from "../../../../services/insurance.service";
import {FormBuilder, FormGroup, ReactiveFormsModule} from "@angular/forms";
import {Router} from "@angular/router";
import {UserService} from "../../../../services/user.service";
import {NgClass} from "@angular/common";
import {PopupService} from "../../../../services/popup.service";
import {PopupType} from "../../../../enums/popup-type";
import {AuthenticationService} from "../../../../services/authentication.service";

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
  constructor(private insuranceService: InsuranceService,
              private authenticationService: AuthenticationService,
              private userService: UserService,
              private popupService: PopupService,
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
    this.userService.getUserById(this.authenticationService.getUserId()).subscribe({
      next: data => {
        this.insuranceForm.value.insurer = data;
        this.insuranceService.addInsurance(this.insuranceForm.value).subscribe({
          next: () => {
            this.popupService.show("Added with success", PopupType.SUCCESS);
            this.router.navigate(['/dashboard/insurances']);
          },
          error: () => {
            this.popupService.show("An error occurred while adding insurance", PopupType.ERROR)
          }
        })
      },
      error: () => {
          this.popupService.show("Can't access to API", PopupType.ERROR)
      }
      })
  }
}
