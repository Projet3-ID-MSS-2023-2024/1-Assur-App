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

@Component({
  selector: 'app-admin-page',
  standalone: true,
  imports: [CommonModule, TableModule, ButtonModule, DropdownModule],
  templateUrl: './admin-page.component.html',
  styleUrl: './admin-page.component.css',
  providers: [DialogService]
})
export class AdminPageComponent implements OnInit, OnDestroy {

  constructor(private userService: UserService, public dialogService: DialogService) { }

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
  }


  getAllUsers() {
    const subscription = this.userService.getAllUser().subscribe(
      (data: any) => {
        console.log(data);
        this.users = data;
        subscription.unsubscribe();
      },
      (err: any) => {
        console.log(err);
      }
    );
  }
  

  // deleteUser(id: number): void {
  //   this.userService.deleteUser(id).subscribe(() => true);
  // }

}
