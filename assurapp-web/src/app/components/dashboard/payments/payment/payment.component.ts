import {Component, ElementRef, NgZone, OnInit, ViewChild} from '@angular/core';
import {SubscriptionService} from "../../../../services/subscription.service";
import {PaymentService} from "../../../../services/payment.service";
import {Subscription} from "../../../../interfaces/subscription";
import {ActivatedRoute, Router} from "@angular/router";
import {CurrencyPipe, DatePipe} from "@angular/common";
import {Payment} from "../../../../interfaces/payment";
import {PaymentStatus} from "../../../../enums/payment-status";

declare var paypal: any;

@Component({
  selector: 'app-payment',
  standalone: true,
  imports: [
    CurrencyPipe,
    DatePipe,
  ],
  templateUrl: './payment.component.html',
  styleUrl: './payment.component.css'
})
export class PaymentComponent implements OnInit {
  @ViewChild('paymentRef', {static: true}) paymentRef!: ElementRef;
  subscription!: Subscription;
  today: Date = new Date();
  duration: number = 0;
  amount: number = 0;
  payed: boolean = false;
  payment!: Payment;
  constructor(private subscriptionService: SubscriptionService,
              private paymentService: PaymentService,
              private ngZone: NgZone,
              private route: ActivatedRoute,
              private router: Router) {}

  ngOnInit(): void {
    const id: number = parseInt(<string>this.route.snapshot.paramMap.get("id"));
    this.subscriptionService.getSubscriptionById(id).subscribe({
      next: data => {
        this.subscription = data;
        this.amountForDuration();
        if (data.payed) {
          let duration: number =
            (new Date(data.endDate).getFullYear() - new Date(data.startDate).getFullYear())
            * 12 + (new Date(data.endDate).getMonth() - new Date(data.startDate).getMonth());
          let endDate = new Date(this.subscription.endDate);
          duration = duration > 12 ? 12: duration;
          this.duration = duration > 12 ? 12: duration;
          endDate.setMonth(new Date(this.subscription.endDate).getMonth() + duration);
          this.subscription.endDate = endDate;
        }
      },
      error: err => console.error(err)
    })
    paypal.Buttons({
      style: {
        layout: 'horizontal',
        color: 'blue',
        label: 'paypal'
      },
      createOrder: (data: any, actions: any) => {
        this.payment = {
          id: 0,
          amount: this.amount,
          transactionDate: new Date(),
          status: PaymentStatus.PENDING,
        }
        this.paymentService.addPayment(this.payment).subscribe({
          next: data => {
            this.payment = data;
            this.subscription.payments.push(this.payment);
            this.subscriptionService.updateSubscription(this.subscription).subscribe({
              next: data => {},
              error: err => console.error(err)
            })
          },
          error: err => console.error(err)
        });
        return actions.order.create({
          purchase_units: [
            {
              amount: {
                value: this.getAmount(),
              },
            },
          ],
        });
      },
      onApprove: (data: any, actions: any) => {
        return actions.order.capture().then((details:any) => {
          this.ngZone.run(() => {
            this.payment.status = PaymentStatus.COMPLETED;
            this.payed = true;
            this.subscription.payed = true;
            this.paymentService.updatePayment(this.payment).subscribe({
              next: data => {
                this.subscriptionService.updateSubscription(this.subscription).subscribe({
                  next: data => {},
                  error: err => console.error(err)
                })
              },
              error: err => console.error(err)
            });
          });
        });
      },
      onError: (err: any) => {
        this.payment.status = PaymentStatus.FAILED;
        this.subscription.payed = true;
        this.paymentService.updatePayment(this.payment).subscribe({
          next: data => {},
          error: err => console.error(err)
        });
        console.error('PayPal Error:', err);
      },
    }).render(this.paymentRef.nativeElement);
  }

  renew(current: Subscription, start: Date, end: Date) {
    let subscription = current;
    subscription.payed = false;
    subscription.startDate = end;
    const duration: number = (end.getFullYear() - start.getFullYear()) * 12 + (end.getMonth() - start.getMonth());
    console.log(duration)
    let nextEnd = new Date(subscription.startDate);
    nextEnd.setMonth(new Date(current.endDate).getMonth() + duration);
    subscription.endDate = nextEnd;
    this.subscription = subscription;
    this.subscriptionService.addSubscription(subscription).subscribe({
      next: data => {
        this.subscription = data;
        this.amountForDuration();
      },
      error: err => console.log(err)
    })
  }

  back() {
    setTimeout(() => this.router.navigate(['/dashboard/payments']),1000)
  }

  amountForDuration() {
    const amount: string = this.subscription.insurance.terms.filter(t => t.name.toUpperCase() === "PREMIUM")[0].description.split(' ')[0];
    const start: Date = new Date(this.subscription.startDate);
    const end: Date = new Date(this.subscription.endDate);
    this.duration = (end.getFullYear() - start.getFullYear()) * 12 + (end.getMonth() - start.getMonth());
    this.duration = this.duration > 12 ? 12 : this.duration;
    this.amount = parseFloat(amount) * this.duration;
  }

  getAmount(): number {
    this.amountForDuration();
    return this.amount;
  }
}
