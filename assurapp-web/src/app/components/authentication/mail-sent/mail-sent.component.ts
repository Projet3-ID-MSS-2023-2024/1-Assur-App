import { Component } from '@angular/core';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {DatePipe} from "@angular/common";
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-mail-sent',
  standalone: true,
  imports: [
    FormsModule,
    ReactiveFormsModule,
    DatePipe,
    RouterLink
  ],
  templateUrl: './mail-sent.component.html',
  styleUrl: './mail-sent.component.css'
})
export class MailSentComponent {
  date:Date = new Date();
  constructor() {
  }
}
