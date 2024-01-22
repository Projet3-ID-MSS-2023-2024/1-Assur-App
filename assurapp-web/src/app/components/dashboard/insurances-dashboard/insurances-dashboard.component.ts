import {Component, OnInit} from '@angular/core';
import {Insurance} from "../../../interfaces/insurance";
import {InsuranceService} from "../../../services/insurance.service";
import {RouterLink} from "@angular/router";
import {CurrencyPipe} from "@angular/common";
import {AuthenticationService} from "../../../services/authentication.service";
import {UserService} from "../../../services/user.service";
import {PopupService} from "../../../services/popup.service";
import {PopupType} from "../../../enums/popup-type";
import {SubscriptionService} from "../../../services/subscription.service";
import {PaginatorModule} from "primeng/paginator";
import {ReactiveFormsModule} from "@angular/forms";
import {InsuranceType} from "../../../enums/insurance-type";

@Component({
  selector: 'app-insurances-dashboard',
  standalone: true,
  imports: [
    RouterLink,
    CurrencyPipe,
    PaginatorModule,
    ReactiveFormsModule
  ],
  templateUrl: './insurances-dashboard.component.html',
  styleUrl: './insurances-dashboard.component.css'
})
export class InsurancesDashboardComponent implements OnInit {
  types:InsuranceType[] = Object.values(InsuranceType);
  insurances: Insurance[] = [];
  data: Insurance[] = [];
  current = 1;
  max = 10;

  constructor(private insuranceService: InsuranceService,
              private authenticationService: AuthenticationService,
              private subscriptionService: SubscriptionService,
              private userService: UserService,
              private popupService: PopupService) {}

  ngOnInit(): void {
    this.fetch();
  }

  filter(event: any) {
    if (event.target.value === "ALL") {
      this.getData();
      return
    }
    this.data = this.insurances.filter(i => i.type === event.target.value);
  }

  fetch() {
    this.userService.getUserById(this.authenticationService.getUserId()).subscribe({
      next: data => {
        this.insuranceService.getInsurancesByInsurer(data).subscribe({
          next: data => {
            this.insurances = data;
            this.getData();
          },
          error: () => this.popupService.show("Can't access to API", PopupType.ERROR)
        })
      },
      error: () =>  this.popupService.show("Can't access to API", PopupType.ERROR)
    })
  }

  delete(id: number) {
    this.subscriptionService.getSubscriptionByInsurer(this.authenticationService.getUserId()).subscribe({
      next: data => {
        if (data.some(s => s.insurance.id == id && new Date(s.endDate) > new Date()))
          this.popupService.show("There is subscriptions linked to this insurance.", PopupType.WARNING)
        else {
          if (!confirm("Are you sure to delete this insurance")) return;
          this.insuranceService.deleteInsurance(id).subscribe({
            next: data => this.fetch(),
            error: () =>  this.popupService.show("Can't delete to API", PopupType.ERROR)
          });
        }
      },
      error: () => this.popupService.show("Can't delete to API", PopupType.ERROR)
    })
  }

  getData() {
    const start = (this.current - 1) * this.max;
    this.data = this.insurances.slice(start, start + this.max);
  }

  next() {
    if (this.current < this.getTotalPages()) {
      this.current++;
      this.getData()
    }
  }
  previous() {
    if (this.current > 1) {
      this.current--;
      this.getData()
    }
  }

  getTotalPages(): number {
    return Math.ceil(this.insurances.length / this.max)

  }

  isNext(): boolean {
    return this.current < this.getTotalPages();
  }

  isPrev(): boolean {
    return this.current > 1;
  }

}
