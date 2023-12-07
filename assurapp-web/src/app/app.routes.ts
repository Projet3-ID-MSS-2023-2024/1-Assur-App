import { Routes } from '@angular/router';
import { DeclareClaimComponent } from './components/declare-claim/declare-claim.component';
import {LoginComponent} from "./components/authentication/login/login.component";
import {RegisterComponent} from "./components/authentication/register/register.component";

export const routes: Routes = [
  { path: "declareclaim", component: DeclareClaimComponent},
  { path: 'login', component: LoginComponent},
  { path: 'register', component: RegisterComponent},
];
