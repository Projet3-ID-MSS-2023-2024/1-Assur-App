import {Component, OnInit} from '@angular/core';
import {ClaimService} from "../../../services/claim.service";
import {Claim} from "../../../interfaces/claim";
import {NgClass, CommonModule} from "@angular/common";
import {RouterLink} from "@angular/router";
import {ClaimStatus} from "../../../enums/claim-status.enum";
import {AuthenticationService} from "../../../services/authentication.service";
import {SubscribeComponent} from "../../insurances/subscribe/subscribe.component";
import {ListExpertsComponent} from "../../expertises/list-experts/list-experts.component";
import {ReactiveFormsModule} from "@angular/forms";

@Component({
  selector: 'app-list-claims',
  standalone: true,
  imports: [CommonModule, RouterLink, NgClass, SubscribeComponent, ListExpertsComponent, ReactiveFormsModule],
  templateUrl: './list-claims.component.html',
  styleUrl: './list-claims.component.css'
})
export class ListClaimsComponent implements OnInit {
  claims: Claim[] = [];
  claimslenght: number = 0;
  currentPage: number = 1;
  itemsPerPage: number = 10
  constructor(private claimService: ClaimService, private AuthService: AuthenticationService) { }
  userRole = this.AuthService.getUserRole();
  userId = this.AuthService.getUserId();
  showButtonToAddExpertise = false;
  showButtonToAssignExpert = false;
  showButtonToAddClaim = false;
  showButtonApprovedAndRefused = false;
  hidden: boolean = true;
  claimSelected!: Claim;


  ngOnInit() {
    if (this.userRole == "CLIENT") {
      this.claimService.getClaimsByClient(this.userId).subscribe({
        next: (claims) => {
          this.claims = claims;
          this.claimslenght = claims.length??0;
          this.showButtonToAddExpertise = false;
          this.showButtonToAssignExpert = false;
          this.showButtonToAddClaim = true;
          this.showButtonApprovedAndRefused = false;
        },
      });
    } else if (this.userRole == "EXPERT") {
      this.claimService.getClaimsByExpert(this.userId).subscribe({
        next: (claims) => {
          this.claims = claims;
          this.claimslenght = claims.length??0;
          this.showButtonToAddExpertise = true;
          this.showButtonToAssignExpert = false;
          this.showButtonToAddClaim = false;
          this.showButtonApprovedAndRefused = false;
        },
      });
    } else if (this.userRole == "INSURER") {
      this.claimService.getClaimByInsurer(this.userId).subscribe({
        next: (claims) => {
          this.claims = claims;
          this.claimslenght = claims.length??0;
          this.showButtonToAddExpertise = false;
          this.showButtonToAssignExpert = true;
          this.showButtonToAddClaim = false;
          this.showButtonApprovedAndRefused = true;
        },
      });
    }
  }

  ifAlreadyAsign(status: ClaimStatus){
   return status == ClaimStatus.PENDING;
  }

  ifAlreadyExpertise(status: ClaimStatus){
    return !(status == ClaimStatus.PROGRESS) && !(status == ClaimStatus.APPROVED) && !(status == ClaimStatus.CANCELLED) && !(status == ClaimStatus.CLOSED);
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
        return "inline-flex items-center gap-x-1.5 py-1.5 px-3 rounded-full text-xs font-medium bg-teal-100 text-teal-800 dark:bg-yellow-800/30 dark:text-yellow-500";
        case ClaimStatus.ASSIGNED:
          return "inline-flex items-center gap-x-1.5 py-1.5 px-3 rounded-full text-xs font-medium bg-teal-100 text-teal-800 dark:bg-teal-800/30 dark:text-teal-500";
      case ClaimStatus.PROGRESS:
        return "inline-flex items-center gap-x-1.5 py-1.5 px-3 rounded-full text-xs font-medium bg-teal-100 text-teal-800 dark:bg-teal-800/30 dark:text-teal-500";
      case ClaimStatus.CLOSED:
        return "inline-flex items-center gap-x-1.5 py-1.5 px-3 rounded-full text-xs font-medium bg-red-50 text-red-500 dark:bg-white/[.05] dark:text-white";
      case ClaimStatus.CANCELLED:
        return "inline-flex items-center gap-x-1.5 py-1.5 px-3 rounded-full text-xs font-medium bg-red-50 text-red-500 dark:bg-white/[.05] dark:text-white";
      default:
        return "py-1 px-1.5 inline-flex items-center gap-x-1 text-xs bg-red-100 text-red-800 font-medium rounded-full";
    }
  }

  showModal(id: number){
    this.hidden = false;
    this.claimSelected = this.claims.find(claim => claim.id === id)??{} as Claim;
  }
}
