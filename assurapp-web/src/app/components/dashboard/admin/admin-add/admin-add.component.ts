import { Component, OnInit} from '@angular/core';
import { User} from "../../../../interfaces/user";
import { UserService} from "../../../../services/user.service";
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
@Component({
  selector: 'app-admin-add',
  standalone: true,
  imports: [FormsModule,ReactiveFormsModule,CommonModule],
  templateUrl: './admin-add.component.html',
  styleUrl: './admin-add.component.css'
})
export class AdminAddComponent implements OnInit{

  user!: User;
  adminAddUser!: FormGroup;
  added: boolean = false;
  error: boolean = false;

  constructor(private userService: UserService,private router: Router,private route: ActivatedRoute, private formBuilder: FormBuilder) { }


  ngOnInit(): void {
    this.adminAddUser = this.formBuilder.group({
      name: ['', Validators.required],
      lastname: ['', Validators.required],
      email: ['', Validators.required],
      role: ['', Validators.required],
      password: ['', Validators.required],
      confirmpassword: ['', Validators.required]
    });

  }


  addUser() {
      const subscription = this.userService.createUser(this.user).subscribe(
        {
          next: (data: any) => {
            console.log(data);
            subscription.unsubscribe();
          },
          error: (err: any) => {
            console.log(err);
          }
        }
      )
  }

  onSubmit() {
    if (this.adminAddUser.valid) {
      if (this.confirmPassword() === null) {
        this.userService.createUser(this.adminAddUser.value).subscribe({
          next: data => {
            this.added = true;
            setTimeout(() => {
              const currentUrl = this.router.url;
  
              if (currentUrl.includes('administration/experts')) {
                this.router.navigate(['/dashboard/administration/experts']);
              } else {
                this.router.navigate(['/dashboard/administration/insurers']);
              }
            }, 1000);
          },
          error: err => {
            this.error = true;
          }
        });
      } else {
        this.error = true;
      }
    } else {
      this.error = true;
    }
  }


  confirmPassword() {
    const password = this.adminAddUser.get('password')?.value;
    const confirmPassword = this.adminAddUser.get('confirmpassword')?.value;

    return password === confirmPassword ? null : { passwordMismatch: true };
  }

}
