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

  login(user: User): Observable<any> {
    return this.http.post<User>(`${environment.api}/login/{id}`, user);
  }


}
