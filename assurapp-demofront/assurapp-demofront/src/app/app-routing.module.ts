import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomepageComponent } from './components/homepage/homepage.component';
import { SinistreComponent } from './components/sinistre/sinistre.component';

const routes: Routes = [
  { path : '', redirectTo: '/sinistre', pathMatch: 'full' },
  { path : 'home', component: HomepageComponent },
  { path : 'sinistre', component: SinistreComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
