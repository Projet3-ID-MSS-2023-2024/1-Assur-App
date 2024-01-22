import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { User } from '../../../../interfaces/user';
import { UserService } from '../../../../services/user.service';
import { RouterLink } from "@angular/router";
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { DropdownModule } from 'primeng/dropdown';
import { PopupService } from '../../../../services/popup.service';
import { PopupType } from '../../../../enums/popup-type';


@Component({
  selector: 'app-admin-manage-expert',
  standalone: true,
  imports: [CommonModule, TableModule, ButtonModule, DropdownModule,RouterLink],
  templateUrl: './admin-manage-expert.component.html',
  styleUrl: './admin-manage-expert.component.css',
})
export class AdminManageExpertComponent implements OnInit {

  users: User[] = [];
  data: User[] = [];
  current = 1;
  max  = 10;

  constructor(private userService: UserService, private popupService: PopupService) { }

  ngOnInit() {
    this.fetch();
  }


  filterInsurers() {
    this.users = this.users.filter(user => user.role?.label === "EXPERT");
  }

  fetch() {
    const subscription = this.userService.getAllUser().subscribe(
      {
        next: (data: any) => {
          this.users = data;
          this.filterInsurers();
          this.getData();
        },
        error: (err: any) => {
          this.popupService.show("Can't access to API", PopupType.ERROR)
        }
      }
    )
  }


  deleteUser(id: number): void {
    if (!confirm("Are you sure to delete this user")) return;
    this.userService.deleteUser(id).subscribe(
      () => {
        this.popupService.show("The user was successfully deleted", PopupType.SUCCESS)
        setTimeout(() => {
          window.location.reload();
        }, 2000);
      },
      (error) => {
        this.popupService.show("Unable to delete user, he still has claims", PopupType.ERROR)
      }
    );
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
