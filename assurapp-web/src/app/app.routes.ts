import { Routes } from '@angular/router';
import {InsuranceComponent} from "./components/insurance/insurance.component";
import {AddInsuranceComponent} from "./components/insurance/add-insurance/add-insurance.component";
import {UpdateInsuranceComponent} from "./components/insurance/update-insurance/update-insurance.component";

export const routes: Routes = [
  {path: 'insurances', component: InsuranceComponent},
  {path: 'insurances/add', component: AddInsuranceComponent},
  {path: 'insurances/update/:id', component: UpdateInsuranceComponent},
];
