import {Component, OnInit} from '@angular/core';
import {NavbarComponent} from "../home/navbar/navbar.component";
import {Router, RouterLink} from "@angular/router";
import {Insurance} from "../../interfaces/insurance";
import {InsuranceService} from "../../services/insurance.service";
import {ReactiveFormsModule} from "@angular/forms";
import {InsuranceType} from "../../enums/insurance-type";
import {AuthenticationService} from "../../services/authentication.service";
import {Roles} from "../../enums/roles";
import {SubscribeComponent} from "./subscribe/subscribe.component";
import {NgClass} from "@angular/common";
import {PopupService} from "../../services/popup.service";
import {PopupType} from "../../enums/popup-type";

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
              private popupService: PopupService,
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
      error: () => this.popupService.show("Can't access the API", PopupType.ERROR)
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
    } else if (!this.authenticationService.isLogged()) {
      this.popupService.show("You are not connected. \nYou will be redirected to login page", PopupType.INFO)
      setTimeout(() => {
       this.router.navigate(['/login'])
      }, 2000);
    } else {
      this.popupService.show("You are not connected as client. \nLogout and retry", PopupType.INFO)
    }
  }
}
