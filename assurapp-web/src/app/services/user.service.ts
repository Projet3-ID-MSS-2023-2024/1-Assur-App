import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from "../../environments/environment.development";
import { Observable } from 'rxjs';
import { User } from '../interfaces/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  bearer = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJza3lAZ21haWwuY29tIiwibmFtZSI6ImFkbWluIiwiZXhwIjoxNzAyOTYwMzExLCJyb2xlIjp7ImlkIjoxLCJsYWJlbCI6IkNMSUVOVCJ9fQ.38DAvy2RdX0zUgzdaA8a0oxaltjRuwGEk1r5NCulzf-QPMnHaUaqJDXSTIal_94_UWVVfxp_YKCSdfHn0sJpvQ"

    getAllUser(): Observable<User[]> {
      
      return this.http.get<User[]>(`${environment.api}/users`, 
      {
        headers: {
          Authorization: `Bearer ${this.bearer}`}
      });
    }

    getUserById(id: number): Observable<User> {
      return this.http.get<User>(`${environment.api}/users/${id}`,
        {
          headers: {
            Authorization: `Bearer ${this.bearer}`
          }
        });
    }

    createUser(user: User): Observable<User> {
      return this.http.post<User>(`${environment.api}/users`, user,
        {
          headers: {
            Authorization: `Bearer ${this.bearer}`
          }
        });
    }

    updateUser(user: User): Observable<User> {
      return this.http.post<User>(`${environment.api}/users/update`, user,
        {
          headers: {
            Authorization: `Bearer ${this.bearer}`
          }
        });
    }

    deleteUser(id: number): Observable<User> {
      
      return this.http.delete<User>(`${environment.api}/users/${id}`, { 
        headers: {
          Authorization: `Bearer ${this.bearer}`
        }
      });
    }

    
}
