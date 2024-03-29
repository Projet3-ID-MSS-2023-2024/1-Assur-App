import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {User} from "../interfaces/user";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment.development";
import {Roles} from "../enums/roles";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {


  constructor(private http: HttpClient) {}

  register(user: User): Observable<User> {

    const header = this.getHeaders()

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

  sendForgotPasswordMail(username: string): Observable<any>{
    const data = {username}
    return this.http.post(`${environment.api}/generatepwdCode`, data)
  }

  changeForgottenPassword(username: string, newPassword: string, code: string): Observable<any>{
    const data = {username, newPassword, code}
    return this.http.post(`${environment.api}/changePasswordByCode`, data)
  }

  changePassword(username: string, newPassword: string, oldPassword: string): Observable<any>{
    const data = {username, newPassword, oldPassword}
    const headers = this.getHeaders()
    return this.http.post(`${environment.api}/changePassword`, data, {headers})
  }

  deleteAccount(username: string){
    const data = {username}
    const headers = this.getHeaders()
    return this.http.put(`${environment.api}/anonymize`, data, {headers})
  }


  saveToken(token: string){
    localStorage.setItem('bearer', token)
  }

  getToken(): string | null{
    return localStorage.getItem('bearer')
  }

  getTokenPayload(){
    const token = this.getToken()
    if(token){
      const elements = token.split('.')
      return JSON.parse(atob(elements[1]))
    }
    return null
  }

  getUserEmail(){
    const payload = this.getTokenPayload()
    if(payload){
      return payload.sub
    }
  }

  getUserId(){
    const payload = this.getTokenPayload()
    if(payload){
      return payload.id
    }
  }

  getUserRole(): string {
    const payload = this.getTokenPayload()
    return payload.role.label
  }

  getHeaders() {
    return new HttpHeaders({
      'Authorization': 'Bearer ' + this.getToken()
    })
  }

  isLogged(): boolean{
    const token = this.getToken()
    return !! token
  }

  logout(){
    localStorage.removeItem('bearer')
  }

  hasPermission(roles: Roles[]): boolean {
    return roles.some(r => r === this.getUserRole());
  }
}
