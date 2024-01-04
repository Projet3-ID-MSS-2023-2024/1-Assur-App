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

  bearer = "eyJhbGciOiJIUzUxMiJ9.eyJuYW1lIjoiQmVuamFtaW4iLCJzdWIiOiJkZWxzaW5uZWJlbmphbWluQGdtYWlsLmNvbSIsInJvbGUiOnsiaWQiOjMsImxhYmVsIjoiRVhQRVJUIn0sImV4cCI6MTcwMzAwNDI4NH0.DdTyDpCn3pD5CYRKAlmFmnfpn2i6WON163ueV0vjhPTJUeu_bGMlARDB6TYXFDZwQTyCAssR-dteuWXm33w62w"

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
      return this.http.post<User>(`${environment.api}/usersUpdate`, user,
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
