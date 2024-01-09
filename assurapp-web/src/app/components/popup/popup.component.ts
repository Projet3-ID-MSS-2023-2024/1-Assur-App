import { Component } from '@angular/core';
import {Popup} from "../../interfaces/popup";
import {PopupService} from "../../services/popup.service";

@Component({
  selector: 'app-popup',
  standalone: true,
  imports: [],
  templateUrl: './popup.component.html',
  styleUrl: './popup.component.css'
})
export class PopupComponent {
  popups: Popup[] = [];

  constructor(private popupService: PopupService) {
    this.popupService.get().subscribe(data => this.popups = data);
  }

  close(id: number): void {
    this.popupService.close(id);
  }
}
