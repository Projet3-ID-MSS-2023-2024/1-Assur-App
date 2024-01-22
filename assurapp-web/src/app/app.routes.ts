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
import {AdminManageInsurerComponent} from './components/dashboard/admin/admin-manage-insurer/admin-manage-insurer.component';
import {AdminManageExpertComponent } from './components/dashboard/admin/admin-manage-expert/admin-manage-expert.component';
import {AdminAddComponent} from './components/dashboard/admin/admin-add/admin-add.component';
import {AdminUpdateComponent} from './components/dashboard/admin/admin-update/admin-update.component';
import { ManageUsersComponent } from './components/dashboard/manage-users/manage-users.component';
import {ForgotPasswordComponent} from "./components/authentication/forgot-password/forgot-password.component";
import {MailSentComponent} from "./components/authentication/mail-sent/mail-sent.component";
import {ForgotPasswordFormStepComponent} from "./components/authentication/forgot-password-form-step/forgot-password-form-step.component";
import {ProfileComponent} from "./components/dashboard/profile/profile.component";
import {ChangePasswordComponent} from "./components/dashboard/profile/change-password/change-password.component";
import {ConfirmDeleteComponent} from "./components/dashboard/profile/confirm-delete/confirm-delete.component";
import {SubscriptionsComponent} from "./components/dashboard/subscriptions/subscriptions.component";
import {PaymentsComponent} from "./components/dashboard/payments/payments.component";
import {PaymentComponent} from "./components/dashboard/payments/payment/payment.component";
import {AuthGuard} from "./guards/auth.guard";
import {Roles} from "./enums/roles";

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
  { path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard], data: {roles: [Roles.ADMINISTRATOR, Roles.EXPERT, Roles.INSURER, Roles.CLIENT]}, children: [
      { path: "", component: ProfileComponent, canActivate: [AuthGuard], data: {roles: [Roles.ADMINISTRATOR, Roles.EXPERT, Roles.INSURER, Roles.CLIENT]}},
      { path: "profile", component: ProfileComponent, canActivate: [AuthGuard], data: {roles: [Roles.ADMINISTRATOR, Roles.EXPERT, Roles.INSURER, Roles.CLIENT]}},
      { path: "profile/changePassword", component: ChangePasswordComponent, canActivate: [AuthGuard], data: {roles: [Roles.ADMINISTRATOR, Roles.EXPERT, Roles.INSURER, Roles.CLIENT]}},
      { path: "profile/delete", component: ConfirmDeleteComponent, canActivate: [AuthGuard], data: {roles: [Roles.ADMINISTRATOR, Roles.EXPERT, Roles.INSURER, Roles.CLIENT]}},
      { path: 'insurances', component: InsurancesDashboardComponent, canActivate: [AuthGuard], data: {roles: [Roles.INSURER, Roles.CLIENT]}},
      { path: 'insurances/clients', component: ManageUsersComponent, canActivate: [AuthGuard], data: {roles: [Roles.INSURER]}},
      { path: 'insurances/add', component: AddInsuranceComponent, canActivate: [AuthGuard], data: {roles: [Roles.INSURER]}},
      { path: 'insurances/update/:id', component: UpdateInsuranceComponent, canActivate: [AuthGuard], data: {roles: [Roles.INSURER]}},
      { path: 'subscriptions', component: SubscriptionsComponent, canActivate: [AuthGuard], data: {roles: [Roles.INSURER, Roles.CLIENT]}},
      { path: 'payments', component: PaymentsComponent, canActivate: [AuthGuard], data: {roles: [Roles.INSURER, Roles.CLIENT]}},
      { path: 'payments/add/:id', component: PaymentComponent, canActivate: [AuthGuard], data: {roles: [Roles.CLIENT]}},
      { path: "claims", component: ListClaimsComponent, canActivate: [AuthGuard], data: {roles: [Roles.EXPERT, Roles.INSURER, Roles.CLIENT]}},
      { path: "claims/add", component: DeclareClaimComponent, canActivate: [AuthGuard], data: {roles: [Roles.EXPERT, Roles.INSURER, Roles.CLIENT]}},
      { path: "claims/:id", component: ShowClaimComponent, canActivate: [AuthGuard], data: {roles: [Roles.EXPERT, Roles.INSURER, Roles.CLIENT]}},
      { path: "expertises", component: ListExpertiseComponent, canActivate: [AuthGuard], data: {roles: [Roles.EXPERT, Roles.INSURER, Roles.CLIENT]}},
      { path: "expertises/add", component: AddExpertiseComponent, canActivate: [AuthGuard], data: {roles: [Roles.EXPERT]}},
      { path: "expertises/add/:id", component: AddExpertiseComponent, canActivate: [AuthGuard], data: {roles: [Roles.EXPERT]}},
      { path: "administration/add", component: AdminAddComponent, canActivate: [AuthGuard], data: {roles: [Roles.ADMINISTRATOR]}},
      { path: "administration/update/:id", component: AdminUpdateComponent, canActivate: [AuthGuard], data: {roles: [Roles.ADMINISTRATOR]}},
      { path: "administration/insurers", component: AdminManageInsurerComponent, canActivate: [AuthGuard], data: {roles: [Roles.ADMINISTRATOR]}},
      { path: "administration/insurers/add", component: AdminAddComponent, canActivate: [AuthGuard], data: {roles: [Roles.ADMINISTRATOR]}},
      { path: "administration/insurers/update/:id", component: AdminUpdateComponent, canActivate: [AuthGuard], data: {roles: [Roles.ADMINISTRATOR]}},
      { path: "administration/experts", component: AdminManageExpertComponent, canActivate: [AuthGuard], data: {roles: [Roles.ADMINISTRATOR]}},
      { path: "administration/experts/add", component: AdminAddComponent, canActivate: [AuthGuard], data: {roles: [Roles.ADMINISTRATOR]}},
      { path: "administration/experts/update/:id", component: AdminUpdateComponent, canActivate: [AuthGuard], data: {roles: [Roles.ADMINISTRATOR]}}
    ]},
];
