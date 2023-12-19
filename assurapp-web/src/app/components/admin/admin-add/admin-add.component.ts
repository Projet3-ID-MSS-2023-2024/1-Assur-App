import { Component } from '@angular/core';
import { UserService } from '../../../services/user.service';
import { User } from '../../../interfaces/user';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-admin-add',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './admin-add.component.html',
  styleUrl: './admin-add.component.css'
})
export class AdminAddComponent {

  constructor(private userService: UserService) { }
  
  user: User = { } as User;
  addUser() {
      
      if (!this.user.id || this.user.id == 0) return;
      if (!this.user.name || this.user.name == '') return; 
      if (!this.user.lastname || this.user.lastname == '') return;
      if (!this.user.email || this.user.email == '') return;
      if (!this.user.password || this.user.password == '') return;
  
      console.log(this.user);
      
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


}
