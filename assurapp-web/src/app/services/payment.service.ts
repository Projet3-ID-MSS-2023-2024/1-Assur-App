import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {AuthenticationService} from "./authentication.service";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment.development";
import {Payment} from "../interfaces/payment";

@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  constructor(private http: HttpClient,
              private authenticationService: AuthenticationService) { }

  getAllPayments(): Observable<Payment[]> {
    const headers = this.authenticationService.getHeaders();
    return this.http.get<Payment[]>(`${environment.api}/payments`, {headers});
  }

  getPaymentById(id: number): Observable<Payment> {
    const headers = this.authenticationService.getHeaders();
    return this.http.get<Payment>(`${environment.api}/payments/${id}`, {headers});
  }

  getPaymentByClient(id: number): Observable<Payment[]> {
    const headers = this.authenticationService.getHeaders();
    return this.http.get<Payment[]>(`${environment.api}/payments/client/${id}`,  {headers});
  }

  getPaymentByInsurer(id: number): Observable<Payment[]> {
    const headers = this.authenticationService.getHeaders();
    return this.http.get<Payment[]>(`${environment.api}/payments/insurer/${id}`,  {headers});
  }

  addPayment(payment: Payment): Observable<Payment> {
    const headers = this.authenticationService.getHeaders();
    return this.http.post<Payment>(`${environment.api}/payments`, payment, {headers});
  }

  updatePayment(payment: Payment): Observable<Payment> {
    const headers = this.authenticationService.getHeaders();
    return this.http.put<Payment>(`${environment.api}/payments`, payment, {headers});
  }

  deletePayment(id: number): Observable<void> {
    const headers = this.authenticationService.getHeaders();
    return this.http.delete<void>(`${environment.api}/payments/${id}`, {headers});
  }
}
