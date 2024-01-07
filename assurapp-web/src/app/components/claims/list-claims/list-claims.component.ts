import {Component, OnInit} from '@angular/core';
import {ClaimService} from "../../../services/claim.service";
import {Claim} from "../../../interfaces/claim";
import {NgClass, CommonModule} from "@angular/common";
import {RouterLink} from "@angular/router";
import {ClaimStatus} from "../../../enums/claim-status.enum";

@Component({
  selector: 'app-list-claims',
  standalone: true,
  imports: [CommonModule, RouterLink, NgClass],
  templateUrl: './list-claims.component.html',
  styleUrl: './list-claims.component.css'
})
export class ListClaimsComponent implements OnInit {
  claims: Claim[] = [];
  claimslenght!: number;
  currentPage: number = 1;
  itemsPerPage: number = 10
  constructor(private claimService: ClaimService) { }

  ngOnInit() {
    this.claimService.getClaims().subscribe({
      next: (claims) => {
        this.claims = claims;
        this.claimslenght = claims.length??0;
      },
      error: (err) => {
        console.log(err);
      },
    });
  }

  calculateItemsToShow(): Claim[] {
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    const endIndex = this.currentPage * this.itemsPerPage;
    return this.claims.slice(startIndex, endIndex);
  }

  changePage(newPage: number): void {
    if (newPage > 0 && newPage <= Math.ceil(this.claims.length / this.itemsPerPage)) {
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
