import { Component, OnInit } from '@angular/core';
import { UserService } from '../../../services/user.service';
import { User } from '../../../interfaces/user';
import { FormsModule } from '@angular/forms';
import { DynamicDialogConfig } from 'primeng/dynamicdialog';

@Component({
  selector: 'app-admin-update',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './admin-update.component.html',
  styleUrl: './admin-update.component.css'
})
export class AdminUpdateComponent  {

  oldUser: User = this.dialogConfig.data.user;
  user: User = {
    id: this.oldUser.id,
    name: this.oldUser.name,
    lastname: this.oldUser.lastname,
    email: this.oldUser.email,
    password: this.oldUser.password,
  };
 

  constructor(private userService: UserService, private dialogConfig: DynamicDialogConfig) { }

  
  updateUser() {

    if (!this.user.id || this.user.id == 0) return;
    if (!this.user.name || this.user.name == '') return; 
    if (!this.user.lastname || this.user.lastname == '') return;
    if (!this.user.email || this.user.email == '') return;
    if (!this.user.password || this.user.password == '') return;

    console.log(this.user);
    console.log(this.oldUser);
    
    const subscription = this.userService.updateUser(this.user).subscribe(
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

  

}
