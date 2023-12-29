import { Component, OnInit} from '@angular/core';
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
export class AdminAddComponent implements OnInit{

  user!: User;

  constructor(private userService: UserService) { }

  
  ngOnInit(): void {
    this.user = {
      id: 0,
      name: '',
      lastname: '',
      email: '',
      password: ''
    }
  }
  

  addUser() {      
  
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
