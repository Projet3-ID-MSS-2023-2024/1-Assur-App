import { Routes } from '@angular/router';
import { DeclareClaimComponent } from './components/declare-claim/declare-claim.component';
import {LoginComponent} from "./components/authentication/login/login.component";
import {RegisterComponent} from "./components/authentication/register/register.component";
import {HomeComponent} from "./components/home/home.component";
import {DashboardComponent} from "./components/dashboard/dashboard.component";
import {InsurancesComponent} from "./components/insurances/insurances.component";
import {ContactComponent} from "./components/contact/contact.component";
import {InsurancesDashboardComponent} from "./components/dashboard/insurances-dashboard/insurances-dashboard.component";
import {AddInsuranceComponent} from "./components/dashboard/insurances-dashboard/add-insurance/add-insurance.component";
import {
  UpdateInsuranceComponent
} from "./components/dashboard/insurances-dashboard/update-insurance/update-insurance.component";

export const routes: Routes = [
  { path: "", component: HomeComponent},
  { path: "declareclaim", component: DeclareClaimComponent},
  { path: 'insurances', component: InsurancesComponent},
  { path: 'contact', component: ContactComponent},
  { path: 'login', component: LoginComponent},
  { path: 'register', component: RegisterComponent},
  { path: 'dashboard', component: DashboardComponent, children: [
      { path: 'insurances', component: InsurancesDashboardComponent},
      { path: 'insurances/add', component: AddInsuranceComponent},
      { path: 'insurances/update/:id', component: UpdateInsuranceComponent},
    ]},
];
