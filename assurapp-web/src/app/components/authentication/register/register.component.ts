import {Component, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {User} from "../../../interfaces/user";
import {AbstractControl, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {Router, RouterLink} from "@angular/router";
import {Role} from "../../../interfaces/role";
import {NavbarComponent} from "../../home/navbar/navbar.component";
import {AuthenticationService} from "../../../services/authentication.service";
import {DataService} from "../../../services/data.service";
import {PopupService} from "../../../services/popup.service";
import {PopupType} from "../../../enums/popup-type";

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
  regex: RegExp = new RegExp("^[0-9\\s+]+$")

  constructor(private authService: AuthenticationService, private router:Router, private dataService: DataService, private popService : PopupService) {
    this.form = new FormGroup({
        name: new FormControl('', [Validators.required]),
        lastname: new FormControl('', [Validators.required]),
        email: new FormControl('', [Validators.required, Validators.email]),
        legalId: new FormControl('', [Validators.required, Validators.minLength(4)]),
        streetAddress: new FormControl('', [Validators.required]),
        city: new FormControl('', [Validators.required]),
        country: new FormControl('', [Validators.required]),
        phoneNumber: new FormControl('+32 ', [Validators.required, Validators.pattern(this.regex), Validators.minLength(8)]),
        password: new FormControl('', [Validators.required, Validators.minLength(7)]),
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
    this.user.address = this.form.value.streetAddress + "%" + this.form.value.city + "%" + this.form.value.country
    this.user.phoneNumber = this.form.value.phoneNumber

    this.authService.register(this.user).subscribe({
      next:(data)=>{
        this.dataService.setSharedData(this.user.email)
        this.router.navigate([`/activate`])
      },
      error:(error) =>{
        this.popService.show("Used Email", PopupType.ERROR)
      }
    })
  }

}
