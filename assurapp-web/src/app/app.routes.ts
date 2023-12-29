import { Routes } from '@angular/router';
import { DeclareClaimComponent } from './components/declare-claim/declare-claim.component';
import {LoginComponent} from "./components/authentication/login/login.component";
import {RegisterComponent} from "./components/authentication/register/register.component";
import {HomeComponent} from "./components/home/home.component";
import { AdminPageComponent } from './components/admin/admin-page/admin-page.component';
import {ActivateAccountComponent} from "./components/authentication/activate-account/activate-account.component";
import {DashboardComponent} from "./components/dashboard/dashboard.component";
import {InsurancesComponent} from "./components/insurances/insurances.component";
import {ContactComponent} from "./components/contact/contact.component";
import {InsurancesDashboardComponent} from "./components/dashboard/insurances-dashboard/insurances-dashboard.component";
import {AddInsuranceComponent} from "./components/dashboard/insurances-dashboard/add-insurance/add-insurance.component";
import {UpdateInsuranceComponent} from "./components/dashboard/insurances-dashboard/update-insurance/update-insurance.component";
import {TermsAndConditionsComponent} from "./components/others/terms-and-conditions/terms-and-conditions.component";
import {PrivacyPolicyComponent} from "./components/others/privacy-policy/privacy-policy.component";

export const routes: Routes = [
  { path: "", component: HomeComponent},
  { path: "adminPage", component: AdminPageComponent},
  { path: "declareclaim", component: DeclareClaimComponent},
  { path: 'insurances', component: InsurancesComponent},
  { path: 'contact', component: ContactComponent},
  { path: 'login', component: LoginComponent},
  {path: 'activate/:email', component: ActivateAccountComponent},
  { path: 'register', component: RegisterComponent},
  {path:'conditions', component: TermsAndConditionsComponent},
  {path:'privacy', component: PrivacyPolicyComponent},
  { path: 'dashboard', component: DashboardComponent, children: [
      { path: 'insurances', component: InsurancesDashboardComponent},
      { path: 'insurances/add', component: AddInsuranceComponent},
      { path: 'insurances/update/:id', component: UpdateInsuranceComponent},
    ]},
];
