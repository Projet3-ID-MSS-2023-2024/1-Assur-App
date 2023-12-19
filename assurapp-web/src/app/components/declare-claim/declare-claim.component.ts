import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {StyleClassModule} from 'primeng/styleclass';
import { ClaimService } from '../../services/claim.service';
import { Claim } from '../../interfaces/claim';
import { ClaimStatus } from '../../interfaces/claim-status.enum';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MessageService} from 'primeng/api';
import { ToastModule } from 'primeng/toast';
import { MessageModule } from 'primeng/message';
import { MessagesModule} from 'primeng/messages';

@Component({
  selector: 'app-declare-claim',
  standalone: true,
  imports: [CommonModule, StyleClassModule, ReactiveFormsModule, FormsModule, MessageModule, MessagesModule, ToastModule],
  templateUrl: './declare-claim.component.html',
  styleUrl: './declare-claim.component.css',
  providers: [MessageService],
})
export class DeclareClaimComponent implements OnInit{
  claim!: Claim;

  constructor(private claimService: ClaimService, private messageService: MessageService) { }

  ngOnInit(): void {
    this.claim = {
      id: 0,
      description: '',
      date: new Date(),
      status: ClaimStatus.PENDING,
    };
  }

  SendDeclaredClaim(){
    this.claimService.createClaim(this.claim).subscribe({
      next: (claim) => {
        console.log(claim);
        this.messageService.add({severity:'success', summary:'Claim declared', detail:'Your claim has been declared'});
      },
      error: (err) => {
        console.log(err);
        this.messageService.add({severity:'error', summary:'Error', detail:'Your claim has not been declared'});
      },
    });
  }

}
