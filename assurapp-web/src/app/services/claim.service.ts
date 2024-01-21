import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Claim } from '../interfaces/claim';
import { environment } from '../../environments/environment.development';
import {AuthenticationService} from "./authentication.service";

@Injectable({
  providedIn: 'root',
})
export class ClaimService {

  constructor(private http: HttpClient, private AuthService: AuthenticationService) { }


  getClaims(): Observable<Claim[]> {
    const headers = this.AuthService.getHeaders();
    return this.http.get<Claim[]>(`${environment.api}/claims`, {headers});
  }

  getClaimsByClient(id: number): Observable<Claim[]> {
      const headers = this.AuthService.getHeaders();
      return this.http.get<Claim[]>(`${environment.api}/claims/user/${id}`, {headers});
  }

  getClaimsByExpert(id: number): Observable<Claim[]> {
      const headers = this.AuthService.getHeaders();
      return this.http.get<Claim[]>(`${environment.api}/claims/expert/${id}`, {headers});
  }

  getClaimById(id: number): Observable<Claim> {
    const headers = this.AuthService.getHeaders();
    return this.http.get<Claim>(`${environment.api}/claims/${id}`, {headers});
  }

  getClaimByInsurer(id: number): Observable<Claim[]> {
    const headers = this.AuthService.getHeaders();
    return this.http.get<Claim[]>(`${environment.api}/claims/insurer/${id}`, {headers});
  }

  getClaimByStatus(status: string): Observable<Claim[]> {
    const headers = this.AuthService.getHeaders();
    return this.http.get<Claim[]>(`${environment.api}/claims/status/${status}`, {headers});
  }

  createClaim(claim: Claim): Observable<Claim> {
    const headers = this.AuthService.getHeaders();
    return this.http.post<Claim>(`${environment.api}/claims`, claim, {headers});
  }

  updateClaim(claim: Claim): Observable<Claim> {
    const headers = this.AuthService.getHeaders();
    return this.http.put<Claim>(`${environment.api}/claims`, claim, {headers});
  }

  deleteClaim(id: number): Observable<Claim> {
    const headers = this.AuthService.getHeaders();
    return this.http.delete<Claim>(`${environment.api}/claims/${id}`, {headers});
  }

  notifyExpert(claim: Claim): Observable<Claim> {
    const headers = this.AuthService.getHeaders();
    return this.http.post<Claim>(`${environment.api}/claims/notifyExpert`, claim, {headers});
  }

  notifyValidation(claim: Claim): Observable<Claim> {
    const headers = this.AuthService.getHeaders();
    return this.http.post<Claim>(`${environment.api}/claims/notifyApproved`, claim, {headers});
  }

  notifyRefused(claim: Claim): Observable<Claim> {
    const headers = this.AuthService.getHeaders();
    return this.http.post<Claim>(`${environment.api}/claims/notifyRefused`, claim, {headers});
  }

}
