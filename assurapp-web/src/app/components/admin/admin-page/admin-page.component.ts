import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserService } from '../../../services/user.service';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { DropdownModule } from 'primeng/dropdown';

import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { AdminAddComponent } from '../admin-add/admin-add.component';
import { AdminUpdateComponent } from '../admin-update/admin-update.component';
import { User } from '../../../interfaces/user';
import { MessageService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';
import { ConfirmPopupModule } from 'primeng/confirmpopup';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ConfirmationService } from 'primeng/api';

@Component({
  selector: 'app-admin-page',
  standalone: true,
  imports: [CommonModule, TableModule, ButtonModule, DropdownModule, ToastModule,ConfirmPopupModule,ConfirmDialogModule],
  templateUrl: './admin-page.component.html',
  styleUrl: './admin-page.component.css',
  providers: [DialogService,MessageService, ConfirmationService]
})
export class AdminPageComponent implements OnInit, OnDestroy {

  constructor(private userService: UserService, public dialogService: DialogService, private messageService: MessageService, private confirmationService: ConfirmationService) { }

  users= [];
  add: DynamicDialogRef | undefined;
  update: DynamicDialogRef | undefined;
  roles: any[] = ['ADMIN', 'SIMPLEUSER', 'INSURER', 'EXPERT'];


  ngOnInit() {
    this.getAllUsers();
  }

  ngOnDestroy(): void {
  }

  showAdd() {
    this.add = this.dialogService.open(AdminAddComponent, {});
  }

  showUpdate(user: User) {
    this.update = this.dialogService.open(AdminUpdateComponent, {
      data: {
        user: user
      }
    });

    this.update.onClose.subscribe((User: User) => {
      if (User) {
          this.messageService.add({ severity: 'info', summary: 'Product Selected', detail: user.name });
      }
  });
  }
 

  getAllUsers() {
    const subscription = this.userService.getAllUser().subscribe(
      {
        next: (data: any) => {
          console.log(data);
          this.users = data;
          subscription.unsubscribe();
        },
        error: (err: any) => {
          this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Failed to get all users' });
        }
      }
    )
  }
  

  deleteUser(id: number): void {
    this.userService.deleteUser(id).subscribe(
      () => {
        this.messageService.add({ severity: 'success', summary: 'Success', detail: 'User Deleted' });

        setTimeout(() => {
          window.location.reload();
        }, 1000);
      },
      (error) => {
        console.error('Erreur lors de la suppression de l\'utilisateur : ', error);
        this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Failed to delete user' });
      }
    );
  }

}
