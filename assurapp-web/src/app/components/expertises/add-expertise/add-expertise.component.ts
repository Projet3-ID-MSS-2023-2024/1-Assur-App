import {Component, Input, OnInit} from '@angular/core';
import {CommonModule} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {PaginatorModule} from "primeng/paginator";
import {ToastModule} from "primeng/toast";
import {MessageService} from "primeng/api";
import {Expertise} from "../../../interfaces/expertise";
import {Claim} from "../../../interfaces/claim";
import {ActivatedRoute, Router} from "@angular/router";
import {ClaimService} from "../../../services/claim.service";
import {ExpertiseService} from "../../../services/expertise.service";

@Component({
  selector: 'app-add-expertise',
  standalone: true,
  imports: [CommonModule, FormsModule, PaginatorModule, ToastModule],
  templateUrl: './add-expertise.component.html',
  styleUrl: './add-expertise.component.css',
  providers: [MessageService]
})
export class AddExpertiseComponent implements OnInit{

  claim!: Claim;
  expertise!: Expertise;

  constructor(private messageService: MessageService, private router: Router, private activatedRoute: ActivatedRoute, private claimService: ClaimService, private expertiseService: ExpertiseService) {
  }
  ngOnInit() {
    const id = this.activatedRoute.snapshot.paramMap.get('id');
    if (id) {
      this.claimService.getClaimById(parseInt(id)).subscribe({
        next: (claim) => {
          this.claim = claim;
          this.expertise = {
            id: 0,
            description: '',
            date: new Date(),
            estimation: 0,
            claim: this.claim,
          };
        },
        error: (err) => {
          console.log(err);
        },
      });
    }
  }

  addExpertise() {
    console.log(this.expertise);
    this.expertiseService.addExpertise(this.expertise).subscribe({
      next: (expertise) => {
      },
      error: (err: any) => {
        console.log(err);
      },
    });

    }

  }
