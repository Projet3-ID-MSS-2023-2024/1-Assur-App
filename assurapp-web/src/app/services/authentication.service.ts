import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {User} from "../interfaces/user";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment.development";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {


  constructor(private http: HttpClient) {}

  register(user: User): Observable<User> {
    return this.http.post<User>(`${environment.api}/register`, user);
  }

  login(username: string, password: string): Observable<any> {
    const credentials = {username, password}
    return this.http.post(`${environment.api}/login`, credentials);
  }

  activateAccount(username: string, code: string):  Observable<any>{
    const data = {username, code}
    return this.http.post(`${environment.api}/verifyAccount`, data)
  }

  regenerateCode(username: string): Observable<any>{
    const data = {username}
    return this.http.post(`${environment.api}/changeActivationCode`, data)
  }

}
