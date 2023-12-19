import { Routes } from '@angular/router';
import { DeclareClaimComponent } from './components/declare-claim/declare-claim.component';
import {LoginComponent} from "./components/authentication/login/login.component";
import {RegisterComponent} from "./components/authentication/register/register.component";
import {HomeComponent} from "./components/home/home.component";
import {DashboardComponent} from "./components/dashboard/dashboard.component";
import {InsurancesComponent} from "./components/insurances/insurances.component";
import {ContactComponent} from "./components/contact/contact.component";
import {ListClaimsComponent} from "./components/claims/list-claims/list-claims.component";
import {AddExpertiseComponent} from "./components/expertises/add-expertise/add-expertise.component";

export const routes: Routes = [
  { path: "", component: HomeComponent},
  { path: "declare-claim", component: DeclareClaimComponent},
  { path: 'list-claim', component: ListClaimsComponent},
  { path: `add-expertise/:id`, component: AddExpertiseComponent},
  { path: 'insurances', component: InsurancesComponent},
  { path: 'contact', component: ContactComponent},
  { path: 'login', component: LoginComponent},
  { path: 'register', component: RegisterComponent},
  { path: 'dashboard', component: DashboardComponent, children: [

    ]},
];
