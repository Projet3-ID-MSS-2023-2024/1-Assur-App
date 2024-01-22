import {Component, OnInit} from '@angular/core';
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
import {UserService} from "../../../services/user.service";
import {AuthenticationService} from "../../../services/authentication.service";
import {PopupService} from "../../../services/popup.service";
import {PopupType} from "../../../enums/popup-type";
import {ClaimStatus} from "../../../enums/claim-status.enum";
import firebase from "firebase/compat";
import {FirebaseService} from "../../../services/firebase.service";
import {v4 as uuidv4} from 'uuid';

@Component({
  selector: 'app-add-expertise',
  standalone: true,
  imports: [CommonModule, FormsModule, PaginatorModule, ToastModule],
  templateUrl: './add-expertise.component.html',
  styleUrl: './add-expertise.component.css',
  providers: [MessageService]
})
export class AddExpertiseComponent implements OnInit{
  selectedFile!: File;
  claim!: Claim;
  expertise!: Expertise;
  loading = false;

  constructor(private fireBaseService: FirebaseService,private popupService: PopupService, private authService: AuthenticationService, private userService: UserService, private router: Router, private activatedRoute: ActivatedRoute, private claimService: ClaimService, private expertiseService: ExpertiseService) {
  }
  ngOnInit() {
    const id = this.activatedRoute.snapshot.paramMap.get('id');
    if (id) {
      this.userService.getUserById(this.authService.getUserId()).subscribe({
        next: (user) => {
          this.claimService.getClaimById(parseInt(id)).subscribe({
            next: (claim) => {
              this.claim = claim;
              this.expertise = {
                id: 0,
                description: '',
                date: new Date(),
                estimation: 0,
                claim: this.claim,
                imageFile: '',
                expert: user,
              };
            },
          });
        },
      });
      }

    }

  onFileSelected(event: any){
    this.selectedFile = event.target.files[0];

    this.fireBaseService.uploadFile(this.selectedFile, uuidv4()).then((url) => {
      this.expertise.imageFile = url;
    });
  }

  addExpertise() {
    this.loading = true;
    this.claim.status = ClaimStatus.PROGRESS;
    this.expertiseService.addExpertise(this.expertise).subscribe({
      next: (expertise) => {
        this.popupService.show("Expertise added", PopupType.INFO)
        this.claimService.updateClaim(this.claim).subscribe({
        });
        setTimeout(() => {
          this.loading = false;

          this.router.navigate(['/dashboard/expertises']);
        }, 2000);
      },
      error: (err: any) => {
        this.popupService.show("Can't access to API", PopupType.ERROR)
      },
    });

    }

  }
