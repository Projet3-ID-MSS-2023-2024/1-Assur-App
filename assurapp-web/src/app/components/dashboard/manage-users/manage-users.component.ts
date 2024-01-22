import { Component, OnInit } from '@angular/core';
import { User } from '../../../interfaces/user';
import { UserService } from '../../../services/user.service';
import { MessageService } from 'primeng/api';
import { ConfirmationService } from 'primeng/api';
import { CommonModule } from '@angular/common';
import { AuthenticationService } from '../../../services/authentication.service';


@Component({
  selector: 'app-manage-users',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './manage-users.component.html',
  styleUrl: './manage-users.component.css',
  providers: [MessageService, ConfirmationService]
})
export class ManageUsersComponent implements OnInit{

  constructor(private userService: UserService, private messageService: MessageService, private authService: AuthenticationService) { }

  ngOnInit() {
    this.fetch();
  }

  userId = this.authService.getUserId();



  users: User[] = [];
  data: User[] = [];

  current = 1;
  max  = 10;


  fetch() {
    const subscription = this.userService.findUserByInsurer(this.userId).subscribe(
      {
        next: (data: any) => {
          this.users = data;
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
