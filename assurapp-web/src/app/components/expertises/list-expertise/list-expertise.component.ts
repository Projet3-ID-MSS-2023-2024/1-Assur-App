import {Component, OnInit} from '@angular/core';
import {NgForOf} from "@angular/common";
import {RouterLink} from "@angular/router";
import {Expertise} from "../../../interfaces/expertise";
import {ExpertiseService} from "../../../services/expertise.service";

@Component({
  selector: 'app-list-expertise',
  standalone: true,
    imports: [
        NgForOf,
        RouterLink
    ],
  templateUrl: './list-expertise.component.html',
  styleUrl: './list-expertise.component.css'
})
export class ListExpertiseComponent implements OnInit{
  expertises!: Expertise[];
  expertiseLenght!: number;

  constructor(private expertiseService: ExpertiseService) { }

  ngOnInit() {
    this.expertiseService.getExpertises().subscribe({
      next: (expertises) => {
        this.expertises = expertises;
        console.log(expertises[0]);
        this.expertiseLenght = expertises.length??0;
      },
      error: (err) => {
        console.log(err);
      },
    });
  }



}
