import { Component } from '@angular/core';
import {PaginatorModule} from "primeng/paginator";
import {AbstractControl, FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {AuthenticationService} from "../../../../services/authentication.service";

@Component({
  selector: 'app-change-password',
  standalone: true,
  imports: [
    PaginatorModule,
    ReactiveFormsModule
  ],
  templateUrl: './change-password.component.html',
  styleUrl: './change-password.component.css'
})
export class ChangePasswordComponent {
  password: string = "";
  oldPassword!: string;
  confirmPassword!: string;
  form: FormGroup;
  error: string="";

  constructor(private aRoute: ActivatedRoute, private route: Router, private authService : AuthenticationService) {
    this.form = new FormGroup({
        oldPassword: new FormControl('', [Validators.required]),
        password: new FormControl('', [Validators.required]),
        confirmPassword: new FormControl('', [Validators.required])
      },{
        validators: this.passwordMatchValidator
      }
    );
  }

  passwordMatchValidator(control: AbstractControl) {
    return control.get('password')?.value === control.get('confirmPassword')?.value ? null : {mismatch: true}
  }

  changePassword(){
    this.password = this.form.value.password
    this.oldPassword = this.form.value.oldPassword
    this.authService.changePassword(this.authService.getUserEmail(), this.password, this.oldPassword).subscribe({
      next:() =>{
        this.authService.logout()
        this.route.navigate(["./login"])
      },
      error:()=>{
        //error popup
      }
    })
  }
}
