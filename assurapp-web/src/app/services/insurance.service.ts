import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Insurance} from "../interfaces/insurance";
import {environment} from "../../environments/environment.development";
import {AuthenticationService} from "./authentication.service";
import {User} from "../interfaces/user";

@Injectable({
  providedIn: 'root'
})
export class InsuranceService {

  constructor(private http: HttpClient,
              private authenticationService: AuthenticationService) { }

  getAllInsurances(): Observable<Insurance[]> {
    const headers = this.authenticationService.getHeaders();
    return this.http.get<Insurance[]>(`${environment.api}/insurances`, {headers});
  }

  getInsurancesByInsurer(user: User): Observable<Insurance[]> {
    const headers = this.authenticationService.getHeaders();
    return this.http.get<Insurance[]>(`${environment.api}/insurances/${user.email}`, {headers});
  }

  getInsuranceById(id: number): Observable<Insurance> {
    const headers = this.authenticationService.getHeaders();
    return this.http.get<Insurance>(`${environment.api}/insurances/${id}`, {headers});
  }

  addInsurance(insurance: Insurance): Observable<Insurance> {
    const headers = this.authenticationService.getHeaders();
    return this.http.post<Insurance>(`${environment.api}/insurances`, insurance, {headers});
  }

  updateInsurance(insurance: Insurance): Observable<Insurance> {
    const headers = this.authenticationService.getHeaders();
    return this.http.put<Insurance>(`${environment.api}/insurances`, insurance, {headers});
  }

  deleteInsurance(id: number): Observable<void> {
    const headers = this.authenticationService.getHeaders();
    return this.http.delete<void>(`${environment.api}/insurances/${id}`, {headers});
  }
}
