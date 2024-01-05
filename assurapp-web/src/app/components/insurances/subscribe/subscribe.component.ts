import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {InsuranceService} from "../../../services/insurance.service";
import {AuthenticationService} from "../../../services/authentication.service";
import {SubscriptionService} from "../../../services/subscription.service";
import {UserService} from "../../../services/user.service";
import {Router} from "@angular/router";
import {Subscription} from "../../../interfaces/subscription";
import {Insurance} from "../../../interfaces/insurance";
import {User} from "../../../interfaces/user";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-subscribe',
  standalone: true,
  imports: [
    FormsModule
  ],
  templateUrl: './subscribe.component.html',
  styleUrl: './subscribe.component.css'
})
export class SubscribeComponent implements OnInit {
  @Input() hide: boolean = false;
  @Input() insurance!: Insurance;
  @Output() hideChange: EventEmitter<boolean> = new EventEmitter<boolean>();
  terms: boolean = false;
  client!: User;
  insurances: Insurance[] = [];
  current: string = "";
  duration: string = "";

  constructor(private insuranceService: InsuranceService,
              private authenticationService: AuthenticationService,
              private subscriptionService: SubscriptionService,
              private userService: UserService,
              private router: Router) {}

  ngOnInit() {
    this.userService.getUserById(this.authenticationService.getUserId()).subscribe({
      next: data => this.client = data,
      error: err => console.log(err)
    });
    this.insuranceService.getInsurancesByClient(this.authenticationService.getUserId()).subscribe({
      next: data => this.insurances = data,
      error: err => console.log(err)
    })
  }

  close(): void {
    this.hide = true;
    this.terms = false;
    this.hideChange.emit(this.hide);
  }

  subscribe(): void {
    if (this.insurances.some(i => i.name === this.insurance.name)) {
      this.router.navigate([`/dashboard/insurances/${this.insurance.id}`]);
    }

    let current: Date = new Date(this.current);
    let end: Date = new Date(current);

    if (current.setHours(0, 0, 0, 0) <  new Date().setHours(0, 0, 0, 0)) current = new Date();
    if (!["3", "6", "12"].some(s => s === this.duration)) this.duration = "12";
    end.setMonth(current.getMonth() + parseInt(this.duration));

    const subscription : Subscription = {
      id: 0,
      startDate: current,
      endDate: end,
      payment: false,
      client: this.client,
      insurance: this.insurance,
      claims: [],
      payments: []
    }

    this.subscriptionService.addSubscription(subscription).subscribe({
      next: data => {
        this.router.navigate([`/dashboard/payments/add/${data.id}`]);
      },
      error: err => console.log(err)
    })
  }
}
