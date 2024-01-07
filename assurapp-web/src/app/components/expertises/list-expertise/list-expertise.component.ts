import {Component, OnInit} from '@angular/core';
import {NgForOf} from "@angular/common";
import {RouterLink} from "@angular/router";
import {Expertise} from "../../../interfaces/expertise";
import {ExpertiseService} from "../../../services/expertise.service";
import {Claim} from "../../../interfaces/claim";
import {ClaimStatus} from "../../../enums/claim-status.enum";

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

  getColor(status: ClaimStatus): string {
    switch (status){
      case ClaimStatus.APPROVED:
        return "inline-flex items-center gap-x-1.5 py-1.5 px-3 rounded-full text-xs font-medium bg-teal-100 text-teal-800 dark:bg-teal-800/30 dark:text-teal-500";
      case ClaimStatus.REFUSED:
        return "inline-flex items-center gap-x-1.5 py-1.5 px-3 rounded-full text-xs font-medium bg-red-100 text-red-800 dark:bg-red-800/30 dark:text-red-500";
      case ClaimStatus.PENDING:
        return "inline-flex items-center gap-x-1.5 py-1.5 px-3 rounded-full text-xs font-medium bg-yellow-100 text-yellow-800 dark:bg-yellow-800/30 dark:text-yellow-500";
      case ClaimStatus.PROGRESS:
        return "inline-flex items-center gap-x-1.5 py-1.5 px-3 rounded-full text-xs font-medium bg-teal-100 text-teal-800 dark:bg-teal-800/30 dark:text-teal-500";
      case ClaimStatus.CLOSED:
        return "inline-flex items-center gap-x-1.5 py-1.5 px-3 rounded-full text-xs font-medium bg-gray-50 text-gray-500 dark:bg-white/[.05] dark:text-white";
      case ClaimStatus.CANCELLED:
        return "inline-flex items-center gap-x-1.5 py-1.5 px-3 rounded-full text-xs font-medium bg-gray-50 text-gray-500 dark:bg-white/[.05] dark:text-white";
      default:
        return "py-1 px-1.5 inline-flex items-center gap-x-1 text-xs bg-red-100 text-red-800 font-medium rounded-full";
    }
  }
}
