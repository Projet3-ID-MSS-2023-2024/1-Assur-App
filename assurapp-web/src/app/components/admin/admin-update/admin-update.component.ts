import { Component, OnInit } from '@angular/core';
import { UserService } from '../../../services/user.service';
import { User } from '../../../interfaces/user';
import { FormsModule } from '@angular/forms';
import { DynamicDialogConfig } from 'primeng/dynamicdialog';
import { DialogService } from 'primeng/dynamicdialog';
import { MessageService } from 'primeng/api';
import { Toast, ToastModule } from 'primeng/toast';
import { DynamicDialogRef } from 'primeng/dynamicdialog';

@Component({
  selector: 'app-admin-update',
  standalone: true,
  imports: [FormsModule,ToastModule],
  templateUrl: './admin-update.component.html',
  styleUrl: './admin-update.component.css',
  providers: [DialogService,MessageService]
})
export class AdminUpdateComponent  {

  oldUser: User = this.dialogConfig.data.user;
  user: User = {
    id: this.oldUser.id,
    name: this.oldUser.name,
    lastname: this.oldUser.lastname,
    email: this.oldUser.email,
    password: this.oldUser.password,
    role: this.oldUser.role,

  };
 

  constructor(private userService: UserService, private dialogConfig: DynamicDialogConfig,public dialogService: DialogService, private messageService: MessageService, private close:DynamicDialogRef) { }

  closeDialog(user: User) {
    this.close.close(user);
}
  
  updateUser() {

    console.log(this.user);
    console.log(this.oldUser);
    
    const subscription = this.userService.updateUser(this.user).subscribe(
      {
        next: (data: User) => {
          console.log(data);
          this.user = data;
          subscription.unsubscribe();
          this.closeDialog(this.user);
          this.messageService.add({severity:'success', summary: 'Success', detail: 'User updated'});
        },
        error: (err: any) => {
          console.log(err);
        }
      }
    )
  }

}
