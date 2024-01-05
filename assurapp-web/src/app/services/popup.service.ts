import { Injectable } from '@angular/core';
import {Observable, Subject, take, timer} from "rxjs";
import {PopupType} from "../enums/popup-type";
import {Popup} from "../interfaces/popup";

@Injectable({
  providedIn: 'root'
})
export class PopupService {
  private popups: Popup[] = [];
  private subject: Subject<Popup[]> = new Subject<Popup[]>();
  private currentId = 0;

  constructor() { }

  get(): Observable<Popup[]> {
    return this.subject.asObservable();
  }

  show(message: string, type: PopupType): void {
    const popup: Popup = { id: this.currentId++, message, type };
    this.popups.push(popup);
    this.update(popup);

    timer(5000).pipe(take(1)).subscribe(() => {
      this.close(popup.id);
    });
  }

  private update(close?: Popup): void {
    this.subject.next([...this.popups]);
  }

  public close(id: number): void {
    this.popups = this.popups.filter(p => p.id !== id);
    this.update();
  }

}
