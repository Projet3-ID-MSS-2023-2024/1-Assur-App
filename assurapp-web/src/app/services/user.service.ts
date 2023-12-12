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

    getAllUser(): Observable<User[]> {
      const bearer = 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJza3lkYnoyM0BnbWFpbC5jb20iLCJyb2xlIjp7ImlkIjoxLCJsYWJlbCI6IlNJTVBMRVVTRVIifSwiZXhwIjoxNzAyNDI2Njc2LCJuYW1lIjoiYmVuIn0.sQBG5IzfhUFAmCoR3H1xZLD7xbs1BgqejjtDs_pFrACsG9lbwKXVHnHMFszeHN6vcwgaSsB0oNqThMR9jxOI5w'
      return this.http.get<User[]>(`${environment.api}/users`, 
      {
        headers: {
          Authorization: `Bearer ${bearer}`}
      });
    }

    getUserById(id: number): Observable<User> {
      return this.http.get<User>(`${environment.api}/users/${id}`);
    }

    createUser(user: User): Observable<User> {
      return this.http.post<User>(`${environment.api}/users`, user);
    }

    updateUser(user: User): Observable<User> {
      return this.http.put<User>(`${environment.api}/users/${user.id}`, user);
    }

  deleteUser(id: number): Observable<User> {
    const bearer = 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJza3lkYnoyM0BnbWFpbC5jb20iLCJyb2xlIjp7ImlkIjoxLCJsYWJlbCI6IlNJTVBMRVVTRVIifSwiZXhwIjoxNzAyNDI2Njc2LCJuYW1lIjoiYmVuIn0.sQBG5IzfhUFAmCoR3H1xZLD7xbs1BgqejjtDs_pFrACsG9lbwKXVHnHMFszeHN6vcwgaSsB0oNqThMR9jxOI5w';

    return this.http.delete<User>(`${environment.api}/users/${id}`, {
      headers: {
        Authorization: `Bearer ${bearer}`
      }
    });
  }

    
}
