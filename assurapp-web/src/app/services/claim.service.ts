import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Claim } from '../interfaces/claim';
import { environment } from '../../environments/environment.development';

@Injectable({
  providedIn: 'root',
})
export class ClaimService {

  constructor(private http: HttpClient) { }


  getClaim(): Observable<Claim[]> {
    return this.http.get<Claim[]>(`${environment.api}/claims`);
  }

  getClaimById(id: number): Observable<Claim> {
    return this.http.get<Claim>(`${environment.api}/claims/${id}`);
  }

  createClaim(claim: Claim): Observable<Claim> {
    return this.http.post<Claim>(`${environment.api}/claims`, claim);
  }

  updateClaim(claim: Claim): Observable<Claim> {
    return this.http.put<Claim>(`${environment.api}/claims/${claim.id}`, claim);
  }

  deleteClaim(id: number): Observable<Claim> {
    return this.http.delete<Claim>(`${environment.api}/claims/${id}`);
  }

}
