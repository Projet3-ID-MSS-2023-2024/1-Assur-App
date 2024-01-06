import {Component, OnInit} from '@angular/core';
import {NgForOf} from "@angular/common";
import {RouterLink} from "@angular/router";
import {Expertise} from "../../../interfaces/expertise";
import {ExpertiseService} from "../../../services/expertise.service";
import {Claim} from "../../../interfaces/claim";

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
  expertises: Expertise[] = [];
  expertiseLenght!: number;
  currentPage: number = 1;
  itemsPerPage: number = 10;

  constructor(private expertiseService: ExpertiseService) { }

  ngOnInit() {
    this.expertiseService.getExpertises().subscribe({
      next: (expertises) => {
        this.expertises = expertises;
        this.expertiseLenght = expertises.length??0;
      },
      error: (err) => {
        console.log(err);
      },
    });
  }

  calculateItemsToShow(): Expertise[] {
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    const endIndex = this.currentPage * this.itemsPerPage;
    return this.expertises.slice(startIndex, endIndex);
  }

  changePage(newPage: number): void {
    if (newPage > 0 && newPage <= Math.ceil(this.expertises.length / this.itemsPerPage)) {
      this.currentPage = newPage;
    }
  }
}
