import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Expertise } from '../interfaces/expertise';
import { environment } from '../../environments/environment.development';
import {AuthenticationService} from "./authentication.service";

@Injectable({
  providedIn: 'root'
})
export class ExpertiseService {

  constructor(private http: HttpClient, private AuthService: AuthenticationService) { }

  getExpertises(): Observable<Expertise[]> {
    const headers = this.AuthService.getHeaders();
    return this.http.get<Expertise[]>(`${environment.api}/expertises`, {headers});
  }

  getExpertiseById(id: number): Observable<Expertise> {
    const headers = this.AuthService.getHeaders();
    return this.http.get<Expertise>(`${environment.api}/expertises/${id}`, {headers});
  }

  getExpertiseByExpert(id: number): Observable<Expertise[]> {
    const headers = this.AuthService.getHeaders();
    return this.http.get<Expertise[]>(`${environment.api}/expertises/expert/${id}`, {headers});
  }

  getExpertiseByInsurer(id: number): Observable<Expertise[]> {
    const headers = this.AuthService.getHeaders();
    return this.http.get<Expertise[]>(`${environment.api}/expertises/insurer/${id}`, {headers});
  }

  addExpertise(expertise: Expertise): Observable<Expertise> {
    const headers = this.AuthService.getHeaders();
    return this.http.post<Expertise>(`${environment.api}/expertises`, expertise, {headers});
  }

  updateExpertise(expertise: Expertise): Observable<Expertise> {
    const headers = this.AuthService.getHeaders();
    return this.http.put<Expertise>(`${environment.api}/expertises/${expertise.id}`, expertise, {headers});
  }

  deleteExpertise(id: number): Observable<Expertise> {
    const headers = this.AuthService.getHeaders();
    return this.http.delete<Expertise>(`${environment.api}/expertises/${id}`, {headers});
  }
}
