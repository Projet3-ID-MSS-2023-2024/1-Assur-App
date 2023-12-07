import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {User} from "../../interfaces/user";
import {Observable} from "rxjs";
import {environment} from "../../../environments/environment.development";

@Injectable({
  providedIn: 'root'
})
export class RegisterService {
  constructor(private http: HttpClient) {}

  register(user: User): Observable<User> {
    return this.http.post<User>(`${environment.api}/register`, user);
  }
}
