import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Route, Router} from "@angular/router";
import {AuthenticationService} from "../../../services/authentication.service";
import {AbstractControl, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";

@Component({
  selector: 'app-forgot-password-form-step',
  standalone: true,
  imports: [FormsModule,  ReactiveFormsModule],
  templateUrl: './forgot-password-form-step.component.html',
  styleUrl: './forgot-password-form-step.component.css'
})
export class ForgotPasswordFormStepComponent implements OnInit{
  data : string | null = "";
  email: string = "";
  code: string = "";
  password: string = "";
  confirmPassword?: string;
  form: FormGroup;
  datas? : string[];
  error: string="";

  constructor(private aRoute: ActivatedRoute, private route: Router, private authService : AuthenticationService) {
    this.form = new FormGroup({
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

  ngOnInit(): void {
    this.data = this.aRoute.snapshot.paramMap.get("param");
    if(this.data){
      this.datas = this.data.split('&');
      this.email = atob(this.datas[0])
      this.code = this.datas[1]
    }
  }

  changePassword(){
    this.password = this.form.value.password
    console.log(this.email, this.password, this.code)
    this.authService.changeForgottenPassword(this.email, this.password, this.code).subscribe({
      next:() =>{
        this.route.navigate(["./login"])
      },
      error:()=>{
        this.error = "Incorrect data please verify the link you pasted"
      }
    })
  }



}
