import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class DataService {
  private sharedData: any;
  constructor() { }

  getSharedData(): any {
    return this.sharedData;
  }

  setSharedData(value: any) {
    this.sharedData = value;
  }
}
