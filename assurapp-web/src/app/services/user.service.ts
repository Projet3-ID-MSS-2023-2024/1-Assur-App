import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from "../../environments/environment.development";
import { Observable } from 'rxjs';
import { User } from '../interfaces/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient, private authService: AuthenticationService) { }

    getAllUser(): Observable<User[]> {
      
      const headers = this.authService.getHeaders();

      return this.http.get<User[]>(`${environment.api}/users`, { headers });
    }

    getUserById(id: number): Observable<User> {
      const headers = this.authService.getHeaders();

      return this.http.get<User>(`${environment.api}/users/${id}`, { headers });
    }

    createUser(user: User): Observable<User> {
      const headers = this.authService.getHeaders();

      return this.http.post<User>(`${environment.api}/users`, user, { headers });
    }

    updateUser(user: User): Observable<User> {
      const headers = this.authService.getHeaders();

      return this.http.post<User>(`${environment.api}/usersUpdate`, user, { headers });
    }

    deleteUser(id: number): Observable<User> {
      const headers = this.authService.getHeaders();

      return this.http.delete<User>(`${environment.api}/users/${id}`, { headers });
    }

    findUserByInsurer(id: number): Observable<User[]> {
      const headers = this.authService.getHeaders();

      return this.http.get<User[]>(`${environment.api}/users/insurer/${id}`, { headers });
    }
}
