import {Injectable, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../../environments/environment.development";
import {RegisterService} from "./register.service";


@Injectable({
  providedIn: 'root'
})
export class ActivateAccountService{

  constructor(private http : HttpClient) { }

  activateAccount(username: string, code: string):  Observable<any>{
    const data = {username, code}
    return this.http.post(`${environment.api}/verifyAccount`, data)
  }

  regenerateCode(username: string): Observable<any>{
    const data = {username}
    return this.http.post(`${environment.api}/changeActivationCode`, data)
  }


}
