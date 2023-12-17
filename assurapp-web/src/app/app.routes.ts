import { Routes } from '@angular/router';
import {InsuranceComponent} from "./components/insurance/insurance.component";
import {AddInsuranceComponent} from "./components/insurance/add-insurance/add-insurance.component";
import {UpdateInsuranceComponent} from "./components/insurance/update-insurance/update-insurance.component";
import { DeclareClaimComponent } from './components/declare-claim/declare-claim.component';
import {LoginComponent} from "./components/authentication/login/login.component";
import {RegisterComponent} from "./components/authentication/register/register.component";
import {HomeComponent} from "./components/home/home.component";
import {ActivateAccountComponent} from "./components/authentication/activate-account/activate-account.component";


export const routes: Routes = [
  { path: "", component: HomeComponent},
  { path: "declareclaim", component: DeclareClaimComponent},
  { path: 'auth/login', component: LoginComponent},
  { path: 'auth/register', component: RegisterComponent},
  {path: 'insurances', component: InsuranceComponent},
  {path: 'insurances/add', component: AddInsuranceComponent},
  {path: 'insurances/update/:id', component: UpdateInsuranceComponent},
  {path: 'auth/activate', component: ActivateAccountComponent},
];
