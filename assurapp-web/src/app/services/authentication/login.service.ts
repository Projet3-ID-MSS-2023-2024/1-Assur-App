import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {User} from "../../interfaces/user";
import {environment} from "../../../environments/environment.development";

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  constructor(private http: HttpClient) {}

  login(username: string, password: string): Observable<any> {
    const credentials = {username, password}
    return this.http.post(`${environment.api}/login`, credentials);
  }


}
