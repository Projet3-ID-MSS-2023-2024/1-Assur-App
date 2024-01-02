import {Component, OnInit} from '@angular/core';
import {NavbarComponent} from "../home/navbar/navbar.component";
import {Router, RouterLink} from "@angular/router";
import {Insurance} from "../../interfaces/insurance";
import {InsuranceService} from "../../services/insurance.service";
import {ReactiveFormsModule} from "@angular/forms";
import {InsuranceType} from "../../enums/insurance-type";
import {AuthenticationService} from "../../services/authentication.service";
import {Roles} from "../../enums/roles";
import {SubscriptionService} from "../../services/subscription.service";
import {Subscription} from "../../interfaces/subscription";
import {UserService} from "../../services/user.service";

@Component({
  selector: 'app-insurances',
  standalone: true,
  imports: [
    NavbarComponent,
    RouterLink,
    ReactiveFormsModule
  ],
  templateUrl: './insurances.component.html',
  styleUrl: './insurances.component.css'
})
export class InsurancesComponent implements OnInit {
  insurancesType: InsuranceType[] = Object.values(InsuranceType);
  insurances: Insurance[] = [];
  data: Insurance[] = [];
  companies: string[] = [];
  type: string = "ALL";
  company: string = "ALL";

  constructor(private insuranceService: InsuranceService,
              private authenticationService: AuthenticationService,
              private subscriptionService: SubscriptionService,
              private userService: UserService,
              private router: Router) {}

  ngOnInit() {
    this.insuranceService.getAllInsurances().subscribe({
      next: data => {
        this.insurances = data;
        this.data = data;
        this.companies = this.insurances.reduce((uniqueCompanies: string[], insurance) => {
          const companyName = insurance.insurer.name;
          if (!uniqueCompanies.includes(companyName)) {
            uniqueCompanies.push(companyName);
          }
          return uniqueCompanies;
        }, []);
      },
      error: err => console.error(err)
    })
  }

  onTypeSelect(event: any) {
    this.insurances = this.data;
    this.type = event.target.value;
    if (event.target.value === 'ALL' && this.company === 'ALL') return;
    if (event.target.value === 'ALL' && this.company !== 'ALL') {
      this.insurances = this.insurances.filter(i => i.insurer.name === this.company);
      return;
    }
    this.insurances = this.data.filter(i => i.type === event.target.value);
    if (this.company === 'ALL') return;
    this.insurances = this.insurances.filter(i => i.insurer.name === this.company);
  }

  onInsurerSelect(event: any) {
    this.insurances = this.data;
    this.company = event.target.value;
    if (event.target.value === 'ALL' && this.type === 'ALL') return;
    if (event.target.value === 'ALL' && this.type !== 'ALL') {
      this.insurances =  this.insurances.filter(i => i.type === this.type);
      return;
    }
    this.insurances = this.data.filter(i => i.insurer.name === event.target.value);
    if (this.type === 'ALL') return;
    this.insurances =  this.insurances.filter(i => i.type === this.type);
  }

  subscribe(insurance: Insurance) {
    if (this.authenticationService.isLogged() && this.authenticationService.getUserRole() === Roles.CLIENT) {


      this.userService.getUserById(this.authenticationService.getUserId()).subscribe({
        next: data => {
          const currentDate = new Date();
          let nextDate = new Date(currentDate);
          nextDate.setFullYear(new Date().getFullYear() + 1);
          const subscription : Subscription = {
            id: 0,
            startDate: new Date(),
            endDate: nextDate,
            payment: false,
            client: data,
            insurance: insurance,
            claims: [],
            payments: []
          }
          this.subscriptionService.addSubscription(subscription).subscribe({
            next: data => {
              this.router.navigate([`/dashboard/payments/${data.id}`]);
            },
            error: err => console.log(err)
          })
        },
        error: err => console.log(err)
      })


    } else this.router.navigate(['/login']);
  }
}
