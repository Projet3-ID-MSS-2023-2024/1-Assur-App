import {Component, OnInit, signal} from '@angular/core';
import {CurrencyPipe, DatePipe} from "@angular/common";
import {Router, RouterLink} from "@angular/router";
import {AuthenticationService} from "../../../services/authentication.service";
import {Roles} from "../../../enums/roles";
import {Payment} from "../../../interfaces/payment";
import {PaymentService} from "../../../services/payment.service";
import {PaymentStatus} from "../../../enums/payment-status";
import {SubscriptionService} from "../../../services/subscription.service";
import {Subscription} from "../../../interfaces/subscription";
import {PopupType} from "../../../enums/popup-type";
import {PopupService} from "../../../services/popup.service";
import {PaginatorModule} from "primeng/paginator";
import {InvoiceService} from "../../../services/invoice.service";

@Component({
  selector: 'app-payments',
  standalone: true,
  imports: [
    DatePipe,
    RouterLink,
    CurrencyPipe,
    PaginatorModule
  ],
  templateUrl: './payments.component.html',
  styleUrl: './payments.component.css'
})
export class PaymentsComponent implements OnInit {
  status: PaymentStatus[] = Object.values(PaymentStatus);
  subscriptions: Subscription[] = [];
  payments: Payment[] = [];
  data: Payment[] = [];
  current: number = 1;
  max: number = 10;
  role: string = "";
  state: string = "ALL";


  constructor(private authenticationService: AuthenticationService,
              private popupService: PopupService,
              private invoiceService: InvoiceService,
              private paymentService: PaymentService,
              private router: Router,
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
      })
      this.subscriptionService.getSubscriptionByInsurer(this.authenticationService.getUserId()).subscribe({
        next: data => this.subscriptions = data,
        error: () => this.popupService.show("Can't access to API", PopupType.ERROR)
      });
    }
  }

  remind(payment: Payment) {
    this.paymentService.notify(payment).subscribe({
      next: () => this.popupService.show("Reminded the client", PopupType.SUCCESS),
      error: () => this.popupService.show("An error occurred while sending email", PopupType.ERROR)
    })
  }

  subscription(id: number): Subscription {
    return this.subscriptions.filter(s => s.payments.some(p => p.id === id))[0];
  }

  filterStatus(event: any) {
    if (event.target.value === "ALL") {
      this.data = this.payments
      return
    }
    this.state = event.target.value;
    this.data = this.payments.filter(p => p.status === event.target.value);
  }

  filterClient(event: any) {
    if (this.state === "ALL") {
      this.data = this.subscriptions
        .filter(subscription =>
          subscription.client.lastname.toLowerCase().includes(event.target.value.toLowerCase())
        )
        .flatMap(subscription => subscription.payments);
    } else {
      this.data = this.subscriptions
        .filter(subscription =>
          subscription.client.lastname.toLowerCase().includes(event.target.value.toLowerCase()) &&
          subscription.payments.some(payment => payment.status === this.state as PaymentStatus)
        )
        .flatMap(subscription => subscription.payments);
    }
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

  invoice(subscription: Subscription, payment: Payment) {
    this.invoiceService.download(subscription.client, subscription.insurance, payment, subscription.startDate, subscription.endDate);
  }
  add(payment: Payment) {
    this.paymentService.push(payment);
    this.router.navigate([`/dashboard/payments/add/${this.subscription(payment.id).id}`]);
  }

  protected readonly PaymentStatus = PaymentStatus;
  protected readonly Roles = Roles;

}
