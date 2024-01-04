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
  selector: 'app-admin-manage-expert',
  standalone: true,
  imports: [CommonModule, TableModule, ButtonModule, DropdownModule, ToastModule,ConfirmPopupModule,ConfirmDialogModule],
  templateUrl: './admin-manage-expert.component.html',
  styleUrl: './admin-manage-expert.component.css',
  providers: [DialogService,MessageService, ConfirmationService]
})
export class AdminManageExpertComponent implements OnInit, OnDestroy {

  constructor(private userService: UserService, public dialogService: DialogService, private messageService: MessageService, private confirmationService: ConfirmationService) { }
  users: User[] = [];
  data: User[] = [];
  add: DynamicDialogRef | undefined;
  update: DynamicDialogRef | undefined;
  current = 1;
  max  = 10;


  ngOnInit() {
    this.fetch();
  }

  ngOnDestroy() {
    if (this.add) {
        this.add.close();
    }

    if(this.update){
      this.update.close();
    }
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


  filterInsurers() {
    this.users = this.users.filter(user => user.role?.label === "EXPERT");
  }
  
  fetch() {
    const subscription = this.userService.getAllUser().subscribe(
      {
        next: (data: any) => {
          console.log(data);
          this.users = data;
          this.filterInsurers();
          this.getData();
        },
        error: (err: any) => {
          this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Failed to get all users' });
        }
      }
    )
  }

  /* -------------------- Pagination -------------------- */

  getData() {
    const start = (this.current - 1) * this.max;
    const end = start + this.max;
    this.data = this.users.slice(start, end);
  }


  next() {
    if (this.current < this.getTotalPages()) {
      this.current++;
      this.getData()
    }
  }
  previous() {
    if (this.current > 1) {
      this.current--;
      this.getData()
    }
  }

  getTotalPages(): number {
    return Math.ceil(this.users.length / this.max)
  }

  isNext(): boolean {
    return this.current < this.getTotalPages();
  }

  isPrev(): boolean {
    return this.current > 1;
  }

  
  /* -------------------- */
  

}
