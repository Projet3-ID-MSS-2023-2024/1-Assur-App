import { Component } from '@angular/core';
import {ClaimService} from "../../../services/claim.service";
import {MessageService} from "primeng/api";
import {Claim} from "../../../interfaces/claim";
import { CommonModule} from "@angular/common";
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-list-claims',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './list-claims.component.html',
  styleUrl: './list-claims.component.css'
})
export class ListClaimsComponent {
  claims!: Claim[];
  claimslenght!: number;

  constructor(private claimService: ClaimService) { }

  ngOnInit() {
    this.claimService.getClaims().subscribe({
      next: (claims) => {
        for (let claim of claims){
          if(claim.status == 0){
            // @ts-ignore
            claim.status = "ACCEPTED";
          }
          if(claim.status == 1){
            // @ts-ignore
            claim.status = "REFUSED";
          }
          if(claim.status == 2){
            // @ts-ignore
            claim.status = "PENDING";
          }
        }
        this.claims = claims;
        this.claimslenght = claims.length??0;
      },
      error: (err) => {
        console.log(err);
      },
    });
  }




}
