import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Insurance} from "../interfaces/insurance";
import {environment} from "../../environments/environment.development";

@Injectable({
  providedIn: 'root'
})
export class InsuranceService {

  constructor(private http: HttpClient) { }

  getAllInsurances(): Observable<Insurance[]> {
    return this.http.get<Insurance[]>(`${environment.api}/insurances`);
  }

  getInsuranceById(id: number): Observable<Insurance> {
    return this.http.get<Insurance>(`${environment.api}/insurances/${id}`);
  }

  addInsurance(insurance: Insurance): Observable<Insurance> {
    return this.http.post<Insurance>(`${environment.api}/insurances`, insurance);
  }

  updateInsurance(insurance: Insurance): Observable<Insurance> {
    return this.http.put<Insurance>(`${environment.api}/insurances`, insurance);
  }

  deleteInsurance(id: number): Observable<void> {
    return this.http.delete<void>(`${environment.api}/insurances/${id}`);
  }

}


