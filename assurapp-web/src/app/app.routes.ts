import { Routes } from '@angular/router';
import {DeclareClaimComponent} from './components/claims/declare-claim/declare-claim.component';
import {LoginComponent} from "./components/authentication/login/login.component";
import {RegisterComponent} from "./components/authentication/register/register.component";
import {HomeComponent} from "./components/home/home.component";
import {ActivateAccountComponent} from "./components/authentication/activate-account/activate-account.component";
import {DashboardComponent} from "./components/dashboard/dashboard.component";
import {InsurancesComponent} from "./components/insurances/insurances.component";
import {ContactComponent} from "./components/contact/contact.component";
import {ListClaimsComponent} from "./components/claims/list-claims/list-claims.component";
import {AddExpertiseComponent} from "./components/expertises/add-expertise/add-expertise.component";
import {ListExpertiseComponent} from "./components/expertises/list-expertise/list-expertise.component";
import {ShowClaimComponent} from "./components/claims/show-claim/show-claim.component";
import {InsurancesDashboardComponent} from "./components/dashboard/insurances-dashboard/insurances-dashboard.component";
import {AddInsuranceComponent} from "./components/dashboard/insurances-dashboard/add-insurance/add-insurance.component";
import {UpdateInsuranceComponent} from "./components/dashboard/insurances-dashboard/update-insurance/update-insurance.component";
import {TermsAndConditionsComponent} from "./components/others/terms-and-conditions/terms-and-conditions.component";
import {PrivacyPolicyComponent} from "./components/others/privacy-policy/privacy-policy.component";
import {ForgotPasswordComponent} from "./components/authentication/forgot-password/forgot-password.component";
import {MailSentComponent} from "./components/authentication/mail-sent/mail-sent.component";
import {ForgotPasswordFormStepComponent} from "./components/authentication/forgot-password-form-step/forgot-password-form-step.component";
import {ManageUsersComponent} from "./components/dashboard/manage-users/manage-users.component";
import {AdminManageInsurerComponent} from "./components/admin/admin-manage-insurer/admin-manage-insurer.component";
import {AdminManageExpertComponent} from "./components/admin/admin-manage-expert/admin-manage-expert.component";
import {AdminAddComponent} from "./components/admin/admin-add/admin-add.component";
import {AdminUpdateComponent} from "./components/admin/admin-update/admin-update.component";
import {ProfileComponent} from "./components/dashboard/profile/profile.component";

export const routes: Routes = [
  { path: "", component: HomeComponent},
  { path: 'insurances', component: InsurancesComponent},
  { path: 'contact', component: ContactComponent},
  { path: 'forgot', component:ForgotPasswordComponent},
  { path: 'mail', component:MailSentComponent},
  { path: 'reset/:param', component:ForgotPasswordFormStepComponent},
  { path: 'login', component: LoginComponent},
  { path: 'register', component: RegisterComponent},
  { path: 'activate', component: ActivateAccountComponent},
  { path: 'conditions', component: TermsAndConditionsComponent},
  { path: 'privacy', component: PrivacyPolicyComponent},
  { path: 'dashboard', component: DashboardComponent, children: [
      { path: 'insurances', component: InsurancesDashboardComponent},
      { path: 'insurances/clients', component: ManageUsersComponent},
      { path: 'insurances/add', component: AddInsuranceComponent},
      { path: 'insurances/update/:id', component: UpdateInsuranceComponent},
      { path: "claims", component: ListClaimsComponent},
      { path: "claims/add", component: DeclareClaimComponent},
      { path: "claims/update/:id", component: ShowClaimComponent},
      { path: "expertises", component: ListExpertiseComponent},
      { path: "expertises/add", component: AddExpertiseComponent},
      { path: "expertises/add/:id", component: AddExpertiseComponent},
      //{ path: "expertises/update/:id", component: ShowClaimComponent},
      { path: "administration/insurers", component: AdminManageInsurerComponent},
      { path: "administration/experts", component: AdminManageExpertComponent},
      { path: "administration/add", component: AdminAddComponent},
      { path: "administration/update/:id", component: AdminUpdateComponent},
      { path: "profile", component: ProfileComponent}
    ]},
];
