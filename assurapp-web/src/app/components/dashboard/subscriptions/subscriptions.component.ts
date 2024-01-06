import {Component, OnInit} from '@angular/core';
import {CurrencyPipe, DatePipe} from "@angular/common";
import {RouterLink} from "@angular/router";
import {AuthenticationService} from "../../../services/authentication.service";
import {SubscriptionService} from "../../../services/subscription.service";
import {Subscription} from "../../../interfaces/subscription";
import {Roles} from "../../../enums/roles";
import {PopupService} from "../../../services/popup.service";
import {PopupType} from "../../../enums/popup-type";
import {PaginatorModule} from "primeng/paginator";

@Component({
  selector: 'app-subscriptions',
  standalone: true,
  imports: [
    CurrencyPipe,
    RouterLink,
    DatePipe,
    PaginatorModule
  ],
  templateUrl: './subscriptions.component.html',
  styleUrl: './subscriptions.component.css'
})
export class SubscriptionsComponent implements OnInit {
  subscriptions: Subscription[] = [];
  data: Subscription[] = [];
  current: number = 1;
  max: number = 10;
  role!: string;
  paymentStatus: number = 0;

  constructor(private authenticationService: AuthenticationService,
              private popupService: PopupService,
              private subscriptionService: SubscriptionService) {}

  ngOnInit(): void {
    this.role = this.authenticationService.getUserRole();
    this.fetch();
  }

  filterStatus(event: any) {
    if (event.target.value === "ALL") {
      this.paymentStatus = 0;
      this.data = this.subscriptions;
      return;
    }
    const status = event.target.value === "PAID";
    this.paymentStatus = status ? 1 : 2;
    this.data = this.subscriptions.filter(s => s.payed === status);
  }

  filterName(event: any) {
    const status = this.paymentStatus > 0 ? this.paymentStatus === 1 : false;
    if (this.paymentStatus > 0)
      this.data = this.subscriptions.filter(s => s.client.lastname.toUpperCase().includes(event.target.value.toUpperCase()) && s.payed === status);
    else
      this.data = this.subscriptions.filter(s => s.client.lastname.toUpperCase().includes(event.target.value.toUpperCase()));
  }

  fetch() {
    if (this.authenticationService.getUserRole() == Roles.CLIENT) {
      this.subscriptionService.getSubscriptionByClient(this.authenticationService.getUserId()).subscribe({
        next: data => {
          this.subscriptions = data;
          this.getData();
        },
        error: () => this.popupService.show("Can't access to API", PopupType.ERROR)
      })
    } else if (this.authenticationService.getUserRole() === Roles.INSURER) {
      this.subscriptionService.getSubscriptionByInsurer(this.authenticationService.getUserId()).subscribe({
        next: data => {
          this.subscriptions = data;
          this.getData();
        },
        error: () => this.popupService.show("Can't access to API", PopupType.ERROR)
      })
    }
  }

  close(subscription: Subscription) {
    if (!confirm("Are you sure to close this subscription")) return;
    subscription.endDate = new Date();
    this.subscriptionService.updateSubscription(subscription).subscribe({
      next: () => this.fetch(),
      error: () => this.popupService.show("Can't access to API", PopupType.ERROR)
    });
  }

  payments(subscription: Subscription): boolean {
    if (subscription.payed) return this.disabled(subscription.endDate);
    return false;
  }

  disabled(date: Date): boolean {
    return new Date(date) < new Date();
  }


  getData() {
    const start = (this.current - 1) * this.max;
    this.data = this.subscriptions.slice(start, start + this.max);
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
    return Math.ceil(this.subscriptions.length / this.max)

  }

  isNext(): boolean {
    return this.current < this.getTotalPages();
  }

  isPrev(): boolean {
    return this.current > 1;
  }

  protected readonly Roles = Roles;
}
