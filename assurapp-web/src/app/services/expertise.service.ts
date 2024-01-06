import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Expertise } from '../interfaces/expertise';
import { environment } from '../../environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class ExpertiseService {

  constructor(private http: HttpClient) { }

  getExpertises(): Observable<Expertise[]> {
    return this.http.get<Expertise[]>(`${environment.api}/expertises`);
  }

  getExpertiseById(id: number): Observable<Expertise> {
    return this.http.get<Expertise>(`${environment.api}/expertises/${id}`);
  }

  addExpertise(expertise: Expertise, image: File): Observable<Expertise> {
    const formData = new FormData();
    formData.append('expertise', new Blob([JSON.stringify(expertise)], {
      type: 'application/json'
    }));
    formData.append('image', image);
    return this.http.post<Expertise>(`${environment.api}/expertises`, formData);
  }

  updateExpertise(expertise: Expertise): Observable<Expertise> {
    return this.http.put<Expertise>(`${environment.api}/expertises/${expertise.id}`, expertise);
  }

  deleteExpertise(id: number): Observable<Expertise> {
    return this.http.delete<Expertise>(`${environment.api}/expertises/${id}`);
  }
}
