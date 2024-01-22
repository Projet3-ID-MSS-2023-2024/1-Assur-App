import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {UserService} from "../../../services/user.service";
import {User} from "../../../interfaces/user";
import {DatePipe, NgForOf, NgIf} from "@angular/common";
import {RouterLink} from "@angular/router";
import {PopupService} from "../../../services/popup.service";
import {PopupType} from "../../../enums/popup-type";
import {ClaimService} from "../../../services/claim.service";
import {Claim} from "../../../interfaces/claim";
import {ClaimStatus} from "../../../enums/claim-status.enum";
import {timeout} from "rxjs";

@Component({
  selector: 'app-list-experts',
  standalone: true,
  imports: [
    DatePipe,
    NgForOf,
    NgIf,
    RouterLink
  ],
  templateUrl: './list-experts.component.html',
  styleUrl: './list-experts.component.css'
})
export class ListExpertsComponent implements OnInit{
  @Input() hide: boolean = false;
  users: User[] = [];
  @Output() hideChange: EventEmitter<boolean> = new EventEmitter<boolean>();
  @Input() claimSelected!: Claim;
  expertSelected!: User;

  constructor(private userService: UserService, private popupService: PopupService, private claimService: ClaimService) { }

ngOnInit() {
  this.userService.getAllUser().subscribe({
    next: (users) => {
      // @ts-ignore
      this.users = users.filter(user => user.role?.label === 'EXPERT');
    },
  });
  }

  asignExpertToAClaim(id: number){
    // @ts-ignore
    this.expertSelected = this.users.find(user => user.id === id);
    this.claimSelected.expert = this.expertSelected;
    this.claimSelected.status = ClaimStatus.ASSIGNED;
    this.claimService.updateClaim(this.claimSelected).subscribe({
      next: (claim) => {
        this.notifyExpert(this.claimSelected);
        this.popupService.show('Expertise assigned to the claim', PopupType.SUCCESS);
        this.hide = true;
        this.hideChange.emit(this.hide);
      },
      error: (err) => {
        this.popupService.show('Error : Expertise not assigned to the claim', PopupType.ERROR);
      },
    });
    }

    notifyExpert(claim: Claim) {
      this.claimService.notifyExpert(claim).subscribe({
        next: (claim) => {
        },
        error: (err) => {
          this.popupService.show('Error : Expert not notified', PopupType.ERROR);
        },
      });
   }

  close(): void {
    this.hide = true;
    this.hideChange.emit(this.hide);
  }
}

