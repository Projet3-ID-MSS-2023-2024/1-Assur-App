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
import {SubscribeComponent} from "./subscribe/subscribe.component";
import {NgClass} from "@angular/common";

@Component({
  selector: 'app-insurances',
  standalone: true,
  imports: [
    NavbarComponent,
    RouterLink,
    ReactiveFormsModule,
    SubscribeComponent,
    NgClass
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
  hidden: boolean = true;
  insurance!: Insurance;
  connected: boolean = true;

  constructor(private insuranceService: InsuranceService,
              private authenticationService: AuthenticationService,
              private router: Router) {}

  ngOnInit() {
    this.connected = this.authenticationService.getUserId() ?? false;
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
      this.insurance = insurance;
      this.hidden = false;
    } else {
     setTimeout(() => {
       this.router.navigate(['/login'])
     }, 5000);
    }
  }
}
