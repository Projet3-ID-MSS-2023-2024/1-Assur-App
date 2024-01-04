import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from "../../../../services/authentication.service";
import {SubscriptionService} from "../../../../services/subscription.service";
import {PaymentService} from "../../../../services/payment.service";
import {Subscription} from "../../../../interfaces/subscription";
import {ActivatedRoute, Router} from "@angular/router";
import {CurrencyPipe, DatePipe} from "@angular/common";

@Component({
  selector: 'app-payment',
  standalone: true,
  imports: [
    CurrencyPipe,
    DatePipe
  ],
  templateUrl: './payment.component.html',
  styleUrl: './payment.component.css'
})
export class PaymentComponent implements OnInit {
  subscription!: Subscription;
  today: Date = new Date();
  duration: number = 0;
  amount: number = 0;
  payed: boolean = false;
  constructor(private authenticationService: AuthenticationService,
              private subscriptionService: SubscriptionService,
              private paymentService: PaymentService,
              private route: ActivatedRoute,
              private router: Router) {}

  ngOnInit(): void {
    const id: number = parseInt(<string>this.route.snapshot.paramMap.get("id"));
    this.subscriptionService.getSubscriptionById(id).subscribe({
      next: data => {
        this.subscription = data;
        this.amountForDuration();
      },
      error: err => console.log(err)
    })
  }

  amountForDuration() {
    const amount: string = this.subscription.insurance.terms.filter(t => t.name.toUpperCase() === "PREMIUM")[0].description.split(' ')[0];
    const start: Date = new Date(this.subscription.startDate);
    const end: Date = new Date(this.subscription.endDate);
    this.duration = (end.getFullYear() - start.getFullYear()) * 12 + (end.getMonth() - start.getMonth());
    this.amount = parseFloat(amount) * this.duration;
  }


  protected readonly Date = Date;
}
