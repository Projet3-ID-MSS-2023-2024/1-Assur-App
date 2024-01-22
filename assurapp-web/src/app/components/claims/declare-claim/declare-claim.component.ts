import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {StyleClassModule} from 'primeng/styleclass';
import { ClaimService } from '../../../services/claim.service';
import { Claim } from '../../../interfaces/claim';
import { ClaimStatus } from '../../../enums/claim-status.enum';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MessageService} from 'primeng/api';
import { ToastModule } from 'primeng/toast';
import { MessageModule } from 'primeng/message';
import { MessagesModule} from 'primeng/messages';
import {AuthenticationService} from "../../../services/authentication.service";
import {InsuranceService} from "../../../services/insurance.service";
import {SubscriptionService} from "../../../services/subscription.service";
import {Subscription} from "../../../interfaces/subscription";
import {PopupType} from "../../../enums/popup-type";
import {PopupService} from "../../../services/popup.service";
import {Router} from "@angular/router";

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
  insurer!: number;
  subscriptions!: Subscription[];
  subscriptionId!: number;
  loading = false;
  constructor(private popupService: PopupService,private claimService: ClaimService,private authService: AuthenticationService, private subscriptionService: SubscriptionService, private router: Router) { }
  userConnected = this.authService.getUserId();

  ngOnInit(): void {
    this.claim = {
      id: 0,
      description: '',
      date: new Date(),
      status: ClaimStatus.PENDING,
      client: this.userConnected,
    };
    this.subscriptionService.getSubscriptionByClient(this.userConnected).subscribe({
      next: (subscription) => {
        this.subscriptions = subscription;
      },
      error: (err) => {
        console.log(err);
      },
    });
  }

  SendDeclaredClaim(){
    this.loading = true;
    let newSubscription = this.subscriptions.find((subscription) => subscription.id == this.subscriptionId);
    if (newSubscription != null) {
      this.claim.client = newSubscription.client;
    this.claim.date = new Date(this.claim.date);
    this.claimService.createClaim(this.claim).subscribe({
      next: (claim) => {
        this.claim = claim;
          // @ts-ignore
        newSubscription.claims.push(this.claim);
          // @ts-ignore
        this.subscriptionService.updateSubscription(newSubscription).subscribe({
            next: () => {
          this.popupService.show("Claim added", PopupType.INFO);
              setTimeout(() => {
                this.loading = false;

                this.router.navigate(['/dashboard/claims']);
              }, 2000);
        },
            error: () => this.popupService.show("Can't access to API", PopupType.ERROR)
          });
      },
      error: () => this.popupService.show("Can't access to API", PopupType.ERROR)
    });
    }
    }

}
