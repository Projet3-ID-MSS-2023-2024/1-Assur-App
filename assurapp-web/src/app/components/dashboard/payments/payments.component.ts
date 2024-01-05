import {Component, OnInit, signal} from '@angular/core';
import {CurrencyPipe, DatePipe} from "@angular/common";
import {RouterLink} from "@angular/router";
import {AuthenticationService} from "../../../services/authentication.service";
import {Roles} from "../../../enums/roles";
import {Payment} from "../../../interfaces/payment";
import {PaymentService} from "../../../services/payment.service";
import {PaymentStatus} from "../../../enums/payment-status";
import {SubscriptionService} from "../../../services/subscription.service";
import {Subscription} from "../../../interfaces/subscription";
import {PopupType} from "../../../enums/popup-type";
import {PopupService} from "../../../services/popup.service";

@Component({
  selector: 'app-payments',
  standalone: true,
  imports: [
    DatePipe,
    RouterLink,
    CurrencyPipe
  ],
  templateUrl: './payments.component.html',
  styleUrl: './payments.component.css'
})
export class PaymentsComponent implements OnInit {
  subscriptions: Subscription[] = [];
  payments: Payment[] = [];
  data: Payment[] = [];
  current: number = 1;
  max: number = 10;
  role: string = "";

  constructor(private authenticationService: AuthenticationService,
              private popupService: PopupService,
              private paymentService: PaymentService,
              private subscriptionService: SubscriptionService) {}

  ngOnInit(): void {
    this.role = this.authenticationService.getUserRole();
    this.fetch();
  }

  fetch() {
    if (this.authenticationService.getUserRole() == Roles.CLIENT) {
      this.paymentService.getPaymentByClient(this.authenticationService.getUserId()).subscribe({
        next: data => {
          this.payments = data;
          this.getData();
        },
        error: () => this.popupService.show("Can't access to API", PopupType.ERROR)
      })
      this.subscriptionService.getSubscriptionByClient(this.authenticationService.getUserId()).subscribe({
        next: data => this.subscriptions = data,
        error: () => this.popupService.show("Can't access to API", PopupType.ERROR)
      });
    } else if (this.authenticationService.getUserRole() === Roles.INSURER) {
      this.paymentService.getPaymentByInsurer(this.authenticationService.getUserId()).subscribe({
        next: data => {
          this.payments = data;
          this.getData();
        },
        error: err => console.error(err)
      })
      this.subscriptionService.getSubscriptionByInsurer(this.authenticationService.getUserId()).subscribe({
        next: data => this.subscriptions = data,
        error: () => this.popupService.show("Can't access to API", PopupType.ERROR)
      });
    }
  }

  close(payment: Payment) {
    if (!confirm("Are you sure to close this subscription")) return;
    this.paymentService.updatePayment(payment).subscribe({
      next: () => this.fetch(),
      error: () => this.popupService.show("Can't access to API", PopupType.ERROR)
    });
  }

  disabled(date: Date): boolean {
    return new Date(date) < new Date();
  }

  remind(payment: Payment) {

  }


  subscription(id: number): Subscription {
    return this.subscriptions.filter(s => s.payments.some(p => p.id === id))[0];
  }


  getData() {
    const start = (this.current - 1) * this.max;
    this.data = this.payments.slice(start, start + this.max);
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
    return Math.ceil(this.payments.length / this.max)

  }

  isNext(): boolean {
    return this.current < this.getTotalPages();
  }

  isPrev(): boolean {
    return this.current > 1;
  }

  protected readonly PaymentStatus = PaymentStatus;
  protected readonly Roles = Roles;
}
