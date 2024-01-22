import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {InsuranceService} from "../../../../services/insurance.service";
import {ActivatedRoute, Router} from "@angular/router";
import {InsuranceType} from "../../../../enums/insurance-type";
import {PopupService} from "../../../../services/popup.service";
import {PopupType} from "../../../../enums/popup-type";

@Component({
  selector: 'app-update-insurance',
  standalone: true,
    imports: [
        FormsModule,
        ReactiveFormsModule
    ],
  templateUrl: './update-insurance.component.html',
  styleUrl: './update-insurance.component.css'
})
export class UpdateInsuranceComponent implements OnInit {
  types:InsuranceType[] = Object.values(InsuranceType);
  insuranceForm!: FormGroup;
  id!: number;
  constructor(private insuranceService: InsuranceService,
              private popupService: PopupService,
              private router: Router,
              private route: ActivatedRoute,
              private formBuilder: FormBuilder) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.id = +params['id'];
      this.fetch();
    });
  }

  fetch() {
    this.insuranceService.getInsuranceById(this.id).subscribe({
      next: data => this.init(data),
      error: () => this.popupService.show("Can't retrive insurance", PopupType.ERROR)
    })
  }

  init(data: any) {
    this.insuranceForm = this.formBuilder.group({
      id: data.id,
      name: data.name,
      type: data.type,
      coverageAmount: data.coverageAmount,
      insurer: data.insurer,
      terms: this.formBuilder.array(data.terms.map((term: any) => this.formBuilder.group(term)))
    })
  }

  onSubmit() {
    this.insuranceService.updateInsurance(this.insuranceForm.value).subscribe({
      next: () => {
        this.popupService.show("Added with success", PopupType.SUCCESS);
        this.router.navigate(['/dashboard/insurances'])
      },
      error: () => {
        this.popupService.show("Can't access to API", PopupType.ERROR)
      }
    })
  }
}
