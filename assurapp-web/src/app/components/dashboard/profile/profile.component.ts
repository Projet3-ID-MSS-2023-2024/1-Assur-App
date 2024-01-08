import {Component, OnInit} from "@angular/core";
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {User} from "../../../interfaces/user";
import {Role} from "../../../interfaces/role";
import {AuthenticationService} from "../../../services/authentication.service";
import {UserService} from "../../../services/user.service";
import {Router, Routes} from "@angular/router";

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [
    FormsModule,
    ReactiveFormsModule
  ],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent implements OnInit{

  user: User = {
    id:0,
    name: '',
    lastname:'',
    email:'',
    password:'',
    role: {
      id:0,
      label:"CLIENT"
    }
  }

  address: string[]=[];
  streetaddress: string = "";
  city: string = "";
  country: string = "";
  role: Role = {
    id:0,
    label:"CLIENT"
  };
  form: FormGroup;

  constructor(private userService: UserService, private authService: AuthenticationService, private route: Router) {
    this.form = new FormGroup({
        streetAddress: new FormControl('', [Validators.required]),
        city: new FormControl('', [Validators.required]),
        country: new FormControl('', [Validators.required]),
        phoneNumber: new FormControl('', [Validators.required])
      }
    );
  }

  ngOnInit(): void {
    this.userService.getUserById(this.authService.getUserId()).subscribe({
      next:(data) =>{
        this.user = data
        if (this.user.role){
          this.role = this.user.role;
        }
        if (this.user.address){
          this.address = this.user.address.split('%')
        }
        this.streetaddress = this.address[0]
        this.city = this.address[1]
        this.country = this.address[2]
        this.form.setValue({
          streetAddress: this.streetaddress,
          city: this.city,
          country: this.country,
          phoneNumber: this.user.phoneNumber
        })
      }
    })
  }

  onSubmit(){
    this.user.address = this.form.value.streetAddress + "%" + this.form.value.city + "%" + this.form.value.country
    this.user.phoneNumber = this.form.value.phoneNumber
    if(this.user){
      this.user.role = this.role;
      this.userService.updateUser(this.user).subscribe(
        {
          next: ()=>{
            location.reload()
          }
        }
      )
    }
  }

  gotoChangePassword(){
    this.route.navigate(['dashboard/profile/password'])
  }
}
