import {Component, OnInit, signal} from '@angular/core';
import {CurrencyPipe, DatePipe} from "@angular/common";
import {RouterLink} from "@angular/router";
import {AuthenticationService} from "../../../services/authentication.service";
import {Roles} from "../../../enums/roles";
import {Payment} from "../../../interfaces/payment";
import {PaymentService} from "../../../services/payment.service";
import {PaymentStatus} from "../../../enums/payment-status";

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
  payments: Payment[] = [];
  data: Payment[] = [];
  current: number = 1;
  max: number = 10;
  role: string = "";

  constructor(private authenticationService: AuthenticationService,
              private paymentService: PaymentService) {}

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
        error: err => console.error(err)
      })
    } else if (this.authenticationService.getUserRole() === Roles.INSURER) {
      this.paymentService.getPaymentByInsurer(this.authenticationService.getUserId()).subscribe({
        next: data => {
          this.payments = data;
          this.getData();
        },
        error: err => console.error(err)
      })
    }
  }

  close(payment: Payment) {
    if (!confirm("Are you sure to close this subscription")) return;
    this.paymentService.updatePayment(payment).subscribe({
      next: data => this.fetch(),
      error: err => console.error(err)
    });
  }

  disabled(date: Date): boolean {
    return new Date(date) < new Date();
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

  remind(payment: Payment) {

  }
}
