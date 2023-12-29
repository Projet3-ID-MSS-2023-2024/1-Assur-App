import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {User} from "../../../interfaces/user";
import {RegisterService} from "../../../services/authentication/register.service";
import {AbstractControl, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {Router, RouterLink} from "@angular/router";
import {Role} from "../../../interfaces/role";
import {NavbarComponent} from "../../home/navbar/navbar.component";

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, ReactiveFormsModule, NavbarComponent],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css',
})
export class RegisterComponent implements OnInit{
  user!: User;
  role!: Role;
  form: FormGroup;

  constructor(private registerService: RegisterService, private router:Router) {
    this.form = new FormGroup({
        name: new FormControl('', [Validators.required, Validators.pattern("[^0-9]")]),
        lastname: new FormControl('', [Validators.required, Validators.pattern("[^0-9]")]),
        email: new FormControl('', [Validators.required, Validators.email]),
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
    this.role = {
      id:0,
      label:"CLIENT"
    }

    this.user = {
      id:0,
      name:'',
      lastname:'',
      email:'',
      password:'',
      role:this.role
    }
  }

  onSubmit(){
    this.user.id = 0;
    console.log(this.user)
    this.registerService.register(this.user).subscribe({
      next:(data)=>{
        console.log(data);
        this.router.navigate([`/activate/${this.user.email}`]) //['/activate', {email: this.user.email}]
      },
      error:(error) =>{
        console.log(error);
      }

    })
  }


}
