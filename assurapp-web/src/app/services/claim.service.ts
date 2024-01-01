import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Claim } from '../interfaces/claim';
import { environment } from '../../environments/environment.development';

@Injectable({
  providedIn: 'root',
})
export class ClaimService {

  constructor(private http: HttpClient) { }


  getClaims(): Observable<Claim[]> {
    return this.http.get<Claim[]>(`${environment.api}/claims`);
  }

  getClaimById(id: number): Observable<Claim> {
    return this.http.get<Claim>(`${environment.api}/claims/${id}`);
  }

  createClaim(claim: Claim, image: File): Observable<Claim> {
    const formData = new FormData();
    formData.append('claim', new Blob([JSON.stringify(claim)], {
      type: 'application/json'
    }));
    formData.append('image', image);

    return this.http.post<Claim>(`${environment.api}/claims`, formData);
  }

  updateClaim(claim: Claim): Observable<Claim> {
    return this.http.put<Claim>(`${environment.api}/claims`, claim);
  }

  deleteClaim(id: number): Observable<Claim> {
    return this.http.delete<Claim>(`${environment.api}/claims/${id}`);
  }

}
