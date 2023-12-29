import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment.development";
import {Subscription} from "../interfaces/subscription";

@Injectable({
  providedIn: 'root'
})
export class SubscriptionService {

  constructor(private http: HttpClient) { }

  getAllSubscriptions(): Observable<Subscription[]> {
    return this.http.get<Subscription[]>(`${environment.api}/subscriptions`);
  }

  getSubscriptionById(id: number): Observable<Subscription> {
    return this.http.get<Subscription>(`${environment.api}/subscriptions/${id}`);
  }

  addSubscription(subscription: Subscription): Observable<Subscription> {
    return this.http.post<Subscription>(`${environment.api}/subscriptions`, subscription);
  }

  updateSubscription(subscription: Subscription): Observable<Subscription> {
    return this.http.put<Subscription>(`${environment.api}/subscriptions`, subscription);
  }

  deleteSubscription(id: number): Observable<void> {
    return this.http.delete<void>(`${environment.api}/subscriptions/${id}`);
  }
}
