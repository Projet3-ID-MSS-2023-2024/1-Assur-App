import {Component, OnInit} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {PaginatorModule} from "primeng/paginator";
import {ToastModule} from "primeng/toast";
import {Claim} from "../../../interfaces/claim";
import {ClaimService} from "../../../services/claim.service";
import {MessageService} from "primeng/api";
import {ActivatedRoute, Router} from "@angular/router";
import {NgOptimizedImage} from "@angular/common";
import {ExpertiseService} from "../../../services/expertise.service";
import {Expertise} from "../../../interfaces/expertise";

@Component({
  selector: 'app-show-claim',
  standalone: true,
  imports: [
    FormsModule,
    PaginatorModule,
    ToastModule,
    NgOptimizedImage
  ],
  templateUrl: './show-claim.component.html',
  styleUrl: './show-claim.component.css',
  providers: [ClaimService, MessageService]
})
export class ShowClaimComponent implements OnInit{
  claim!: Claim;
  expertise!: Expertise;

  constructor(private router: Router, private activatedRoute: ActivatedRoute, private claimService: ClaimService, private expertService: ExpertiseService) { }

  ngOnInit(): void {
    const id = this.activatedRoute.snapshot.paramMap.get('id');
    if (id){
      this.expertService.getExpertiseById(parseInt(id)).subscribe({
        next: (expertise) => {
          this.expertise = expertise;
          this.claimService.getClaimById(expertise.claim.id).subscribe({
                  next: (claim) => {
                    this.claim = claim;
                  },
                  error: (err) => {
                    console.log(err);
                  },
                });
        },
        error: (err) => {
          console.log(err);
        },
      });
      }
    }


}
