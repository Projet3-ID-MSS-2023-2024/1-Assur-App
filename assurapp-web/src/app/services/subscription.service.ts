import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment.development";
import {Subscription} from "../interfaces/subscription";
import {AuthenticationService} from "./authentication.service";
import {User} from "../interfaces/user";

@Injectable({
  providedIn: 'root'
})
export class SubscriptionService {

  constructor(private http: HttpClient,
              private authenticationService: AuthenticationService) { }

  getAllSubscriptions(): Observable<Subscription[]> {
    const headers = this.authenticationService.getHeaders();
    return this.http.get<Subscription[]>(`${environment.api}/subscriptions`, {headers});
  }

  getSubscriptionById(id: number): Observable<Subscription> {
    const headers = this.authenticationService.getHeaders();
    return this.http.get<Subscription>(`${environment.api}/subscriptions/${id}`, {headers});
  }

  getSubscriptionByClient(id: number): Observable<Subscription[]> {
    const headers = this.authenticationService.getHeaders();
    return this.http.get<Subscription[]>(`${environment.api}/subscriptions/client/${id}`,  {headers});
  }

  getSubscriptionByInsurer(id: number): Observable<Subscription[]> {
    const headers = this.authenticationService.getHeaders();
    return this.http.get<Subscription[]>(`${environment.api}/subscriptions/insurer/${id}`,  {headers});
  }

  addSubscription(subscription: Subscription): Observable<Subscription> {
    const headers = this.authenticationService.getHeaders();
    return this.http.post<Subscription>(`${environment.api}/subscriptions`, subscription, {headers});
  }

  updateSubscription(subscription: Subscription): Observable<Subscription> {
    const headers = this.authenticationService.getHeaders();
    return this.http.put<Subscription>(`${environment.api}/subscriptions`, subscription, {headers});
  }

  deleteSubscription(id: number): Observable<void> {
    const headers = this.authenticationService.getHeaders();
    return this.http.delete<void>(`${environment.api}/subscriptions/${id}`, {headers});
  }
}
