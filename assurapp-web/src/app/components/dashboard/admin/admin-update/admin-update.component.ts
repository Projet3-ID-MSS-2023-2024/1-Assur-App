import { Component, OnInit } from '@angular/core';
import { User } from '../../../../interfaces/user';
import { UserService } from '../../../../services/user.service';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Validators } from '@angular/forms';


@Component({
  selector: 'app-admin-update',
  standalone: true,
  imports: [FormsModule,ReactiveFormsModule, CommonModule],
  templateUrl: './admin-update.component.html',
  styleUrl: 'admin-update.component.css'
})
export class AdminUpdateComponent implements OnInit  {
  user: User [] = [];
  adminUserUpdate!: FormGroup;
  id!: number;
  added: boolean = false;
  error: boolean = false;

  constructor(private userService: UserService,private router: Router,private route: ActivatedRoute, private formBuilder: FormBuilder) { }


  ngOnInit(): void {

    this.id = this.route.snapshot.params['id'];
 
    this.adminUserUpdate = this.formBuilder.group({
      id: [this.id],
      name: ['', Validators.required],
      lastname: ['', Validators.required],
      email: ['', Validators.required],
      role: ['', Validators.required],
      password: ['', Validators.required],
      confirmpassword: ['', Validators.required]
    });

    this.fetch();

  }

  fetch(){
    this.userService.getUserById(this.id).subscribe({
      next: data => this.init(data),
      error: err => console.error(err)
    })
  }


  init(data: any) {
    this.id = data.id;
  
    this.adminUserUpdate = this.formBuilder.group({
      id: [data.id],
      name: [data.name, Validators.required],
      lastname: [data.lastname, Validators.required],
      email: [data.email, Validators.required],
      role: [data.role, Validators.required],
      password: [data.password, Validators.required],
      confirmpassword: [data.password, Validators.required]
    });
  }

  onSubmit() {
    this.userService.updateUser(this.adminUserUpdate.value).subscribe({
      next: data => {
        this.added = true;
        setTimeout(() => {
          const currentUrl = this.router.url;

          if (currentUrl.includes('administration/experts')) {
            this.router.navigate(['/dashboard/administration/experts']);
          } else {
            this.router.navigate(['/dashboard/administration/insurers']);
          }
        },1000);
      },
      error: err => {
        this.error = true
      }
    })
  }


}
