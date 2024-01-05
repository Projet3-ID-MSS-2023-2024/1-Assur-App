import {Component, OnInit} from '@angular/core';
import {CurrencyPipe, DatePipe} from "@angular/common";
import {RouterLink} from "@angular/router";
import {AuthenticationService} from "../../../services/authentication.service";
import {SubscriptionService} from "../../../services/subscription.service";
import {Subscription} from "../../../interfaces/subscription";
import {Roles} from "../../../enums/roles";

@Component({
  selector: 'app-subscriptions',
  standalone: true,
  imports: [
    CurrencyPipe,
    RouterLink,
    DatePipe
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

  constructor(private authenticationService: AuthenticationService,
              private subscriptionService: SubscriptionService) {}

  ngOnInit(): void {
    this.role = this.authenticationService.getUserRole();
    this.fetch();
  }

  fetch() {
    if (this.authenticationService.getUserRole() == Roles.CLIENT) {
      this.subscriptionService.getSubscriptionByClient(this.authenticationService.getUserId()).subscribe({
        next: data => {
          this.subscriptions = data;
          this.getData();
        },
        error: err => console.error(err)
      })
    } else if (this.authenticationService.getUserRole() === Roles.INSURER) {
      this.subscriptionService.getSubscriptionByInsurer(this.authenticationService.getUserId()).subscribe({
        next: data => {
          this.subscriptions = data;
          this.getData();
        },
        error: err => console.error(err)
      })
    }
  }

  close(subscription: Subscription) {
    if (!confirm("Are you sure to close this subscription")) return;
    subscription.endDate = new Date();
    this.subscriptionService.updateSubscription(subscription).subscribe({
      next: data => this.fetch(),
      error: err => console.error(err)
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
