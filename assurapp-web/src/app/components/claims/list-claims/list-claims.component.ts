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
        return "py-1 px-1.5 inline-flex items-center gap-x-1 text-xs bg-teal-100 text-teal-800 font-medium rounded-full";
      case ClaimStatus.REFUSED:
        return "py-1 px-1.5 inline-flex items-center gap-x-1 text-xs bg-red-100 text-red-800 font-medium rounded-full";
      case ClaimStatus.PENDING:
        return "py-1 px-1.5 inline-flex items-center gap-x-1 text-xs bg-orange-100 text-orange-800 font-medium rounded-full";
      case ClaimStatus.PROGRESS:
        return "py-1 px-1.5 inline-flex items-center gap-x-1 text-xs bg-coolGray-300 text-coolGray-800 font-medium rounded-full";
      case ClaimStatus.CLOSED:
        return "py-1 px-1.5 inline-flex items-center gap-x-1 text-xs bg-red-100 text-red-800 font-medium rounded-full";
      case ClaimStatus.CANCELLED:
        return "py-1 px-1.5 inline-flex items-center gap-x-1 text-xs bg-red-100 text-red-800 font-medium rounded-full";
      default:
        return "py-1 px-1.5 inline-flex items-center gap-x-1 text-xs bg-red-100 text-red-800 font-medium rounded-full";
    }
  }

}
