import { Component, OnInit } from '@angular/core';
import { User } from '../../../../interfaces/user';
import { UserService } from '../../../../services/user.service';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Validators } from '@angular/forms';
import { PopupService } from '../../../../services/popup.service';
import { PopupType } from '../../../../enums/popup-type';


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
  selectedRole!: string;

  constructor(private userService: UserService,private router: Router,private route: ActivatedRoute, private formBuilder: FormBuilder, private popupService: PopupService) { }


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

    this.checkRole(this.selectedRole);
    this.fetch();

  }

  fetch(){
    this.userService.getUserById(this.id).subscribe({
      next: data => this.init(data),
    })
  }

  checkRole(role: string) {
    const currentUrl = this.router.url;

    if (currentUrl.includes('/insurers')) {
      this.selectedRole = 'INSURER';
    } else if (currentUrl.includes('/experts')) {
      this.selectedRole = 'EXPERT';
    }

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
        this.popupService.show("The user has been successfully updated", PopupType.SUCCESS)
        setTimeout(() => {
          const currentUrl = this.router.url;

          if (currentUrl.includes('administration/experts')) {
            this.router.navigate(['/dashboard/administration/experts']);
          } else {
            this.router.navigate(['/dashboard/administration/insurers']);
          }
        },100);
      },
      error: err => {
        this.popupService.show("User has not been updated", PopupType.ERROR)
      }
    })
  }


}
