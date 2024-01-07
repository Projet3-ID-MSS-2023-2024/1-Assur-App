import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {User} from "../../../interfaces/user";
import {AbstractControl, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {Router, RouterLink} from "@angular/router";
import {Role} from "../../../interfaces/role";
import {NavbarComponent} from "../../home/navbar/navbar.component";
import {AuthenticationService} from "../../../services/authentication.service";
import {DataService} from "../../../services/data.service";

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

  constructor(private authService: AuthenticationService, private router:Router, private dataService: DataService) {
    this.form = new FormGroup({
        name: new FormControl('', [Validators.required]),
        lastname: new FormControl('', [Validators.required]),
        email: new FormControl('', [Validators.required, Validators.email]),
        legalId: new FormControl('', [Validators.required]),
        streetAddress: new FormControl('', [Validators.required]),
        city: new FormControl('', [Validators.required]),
        country: new FormControl('', [Validators.required]),
        phoneNumber: new FormControl('', [Validators.required]),
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
      name: '',
      lastname:'',
      email:'',
      password:'',
      role:this.role
    }
  }

  onSubmit(){
    this.user.id = 0;
    this.user.name = this.form.value.name
    this.user.lastname = this.form.value.lastname
    this.user.email = this.form.value.email
    this.user.password = this.form.value.password
    this.user.legalId = this.form.value.legalId
    this.user.address = this.form.value.streetAddress + "," + this.form.value.city + "," + this.form.value.country
    this.user.phoneNumber = this.form.value.phoneNumber
    console.log(this.user)
    this.authService.register(this.user).subscribe({
      next:(data)=>{
        this.dataService.setSharedData(this.user.email)
        this.router.navigate([`/activate`])
      },
      error:(error) =>{
        console.log(error);
      }
    })
  }
}
