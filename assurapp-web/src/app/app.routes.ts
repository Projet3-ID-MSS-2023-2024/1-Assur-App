import { Routes } from '@angular/router';
import {InsuranceComponent} from "./components/insurance/insurance.component";
import {AddInsuranceComponent} from "./components/insurance/add-insurance/add-insurance.component";
import {UpdateInsuranceComponent} from "./components/insurance/update-insurance/update-insurance.component";
import { DeclareClaimComponent } from './components/declare-claim/declare-claim.component';
import {LoginComponent} from "./components/authentication/login/login.component";
import {RegisterComponent} from "./components/authentication/register/register.component";
import {HomeComponent} from "./components/home/home.component";
import { AdminPageComponent } from './components/admin/admin-page/admin-page.component';

export const routes: Routes = [
  { path: "", component: HomeComponent},
  { path: "adminPage", component: AdminPageComponent},
  { path: "declareclaim", component: DeclareClaimComponent},
  { path: 'login', component: LoginComponent},
  { path: 'register', component: RegisterComponent},
  {path: 'insurances', component: InsuranceComponent},
  {path: 'insurances/add', component: AddInsuranceComponent},
  {path: 'insurances/update/:id', component: UpdateInsuranceComponent},
];
