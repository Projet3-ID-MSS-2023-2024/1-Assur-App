import {Component, OnInit} from '@angular/core';
import {NgForOf, NgIf} from "@angular/common";
import {Router, RouterLink} from "@angular/router";
import {Expertise} from "../../../interfaces/expertise";
import {ExpertiseService} from "../../../services/expertise.service";
import {ClaimStatus} from "../../../enums/claim-status.enum";
import {AuthenticationService} from "../../../services/authentication.service";
import {ClaimService} from "../../../services/claim.service";
import { Claim } from '../../../interfaces/claim';
import { PopupService } from '../../../services/popup.service';
import { PopupType } from '../../../enums/popup-type';

@Component({
  selector: 'app-list-expertise',
  standalone: true,
  imports: [
    NgForOf,
    RouterLink,
    NgIf
  ],
  templateUrl: './list-expertise.component.html',
  styleUrl: './list-expertise.component.css'
})
export class ListExpertiseComponent implements OnInit {
  expertises: Expertise[] = [];
  expertiseLenght!: number;
  currentPage: number = 1;
  itemsPerPage: number = 10;
  claimId!: number;

  constructor(private expertiseService: ExpertiseService, private router: Router, private AuthService: AuthenticationService, private claimService: ClaimService, private popupService: PopupService) {
  }

  userRole = this.AuthService.getUserRole()
  userId = this.AuthService.getUserId()
  showButtonApprovedAndRefused = false;

  ngOnInit() {
    if (this.userRole == "CLIENT") {
      this.expertiseService.getExpertises().subscribe({
        next: (expertises) => {
          this.expertises = expertises;
          this.expertiseLenght = expertises.length ?? 0;
          this.showButtonApprovedAndRefused = false;
        },
        error: (err) => {
          console.log(err);
        },
      });
    } else if (this.userRole == "EXPERT") {
      this.expertiseService.getExpertiseByExpert(this.AuthService.getUserId()).subscribe({
        next: (expertises) => {
          this.expertises = expertises;
          this.expertiseLenght = expertises.length ?? 0;
          this.showButtonApprovedAndRefused = false;
        },
        error: (err) => {
          console.log(err);
        },
      });
    } else if (this.userRole == "INSURER") {
      this.expertiseService.getExpertiseByInsurer(this.userId).subscribe({
        next: (expertises) => {
          this.expertises = expertises;
          this.expertiseLenght = expertises.length ?? 0;
          this.showButtonApprovedAndRefused = true;
        },
        error: (err) => {
          console.log(err);
        },
      });
    }

  }


  IsNeededApprovisation(status: ClaimStatus) {
    return status == ClaimStatus.PROGRESS;
  }

  Approve(claim: Claim){
    claim.status = ClaimStatus.APPROVED;
    this.claimService.updateClaim(claim).subscribe({
      next: (claim) => {
        this.ngOnInit();
        this.popupService.show("The claim has been validated", PopupType.SUCCESS)
        this.notifyApproved(claim);
      },
      error: (err) => {
        this.popupService.show("The claim could not be validated", PopupType.ERROR)
      },
    });
    }

  Refuse(claim: Claim, expertise: Expertise){
    claim.status = ClaimStatus.REFUSED;
    this.claimService.updateClaim(claim).subscribe({
      next: (claim) => {
        this.ngOnInit();
        this.popupService.show("The claim was refused", PopupType.SUCCESS)
        this.notifyRefused(claim);
      },
      error: (err) => {
        this.popupService.show("The claim could not be refused", PopupType.ERROR)
      },
    });

    this.expertiseService.deleteExpertise(expertise.id).subscribe({
      next: (expertise) => {
        this.ngOnInit();
      },
      error: (err) => {
        console.log(err);
      },
    });
    }

    notifyApproved(claim: Claim){
      this.claimService.notifyValidation(claim).subscribe({
        next: (claim) => {
          this.ngOnInit();
        },
        error: (err) => {
          console.log(err);
        },
      });
    }

    notifyRefused(claim: Claim){
      this.claimService.notifyRefused(claim).subscribe({
        next: (claim) => {
          this.ngOnInit();
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
      case ClaimStatus.ASSIGNED:
        return "inline-flex items-center gap-x-1.5 py-1.5 px-3 rounded-full text-xs font-medium bg-teal-100 text-teal-800 dark:bg-blue-800/30 dark:text-blue-500";
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
