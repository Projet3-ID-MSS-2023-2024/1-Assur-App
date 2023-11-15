import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Sinistre } from '../models/sinistre';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SinistreService {

  constructor(private http: HttpClient) { }

  getSinistres(): Observable<Sinistre[]> {
    return this.http.get<Sinistre[]>(`${environment.api}sinistre`);
  }

  getSinistre(id: number): Observable<Sinistre> {
    return this.http.get<Sinistre>(`${environment.api}sinistre/${id}`);
  }

  createSinistre(sinistre: Sinistre): Observable<Sinistre> {
    return this.http.post<Sinistre>(`${environment.api}sinistre`, sinistre);
  }

  updateSinistre(id: number, sinistre: Sinistre): Observable<Sinistre> {
    return this.http.put<Sinistre>(`${environment.api}sinistre/${id}`, sinistre);
  }

  deleteSinistre(id: number): Observable<Sinistre> {
    return this.http.delete<Sinistre>(`${environment.api}sinistre/${id}`);
  }
}
