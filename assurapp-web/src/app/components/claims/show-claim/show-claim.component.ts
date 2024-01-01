import {Component, OnInit} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {PaginatorModule} from "primeng/paginator";
import {ToastModule} from "primeng/toast";
import {Claim} from "../../../interfaces/claim";
import {ClaimService} from "../../../services/claim.service";
import {MessageService} from "primeng/api";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-show-claim',
  standalone: true,
    imports: [
        FormsModule,
        PaginatorModule,
        ToastModule
    ],
  templateUrl: './show-claim.component.html',
  styleUrl: './show-claim.component.css',
  providers: [ClaimService, MessageService]
})
export class ShowClaimComponent implements OnInit{
  claim!: Claim;

  constructor(private messageService: MessageService, private router: Router, private activatedRoute: ActivatedRoute, private claimService: ClaimService) { }

  ngOnInit(): void {
    const id = this.activatedRoute.snapshot.paramMap.get('id');
    if (id) {
      this.claimService.getClaimById(parseInt(id)).subscribe({
        next: (claim) => {
          this.claim = claim;
        },
        error: (err) => {
          console.log(err);
        },
      });
    }
}
}
