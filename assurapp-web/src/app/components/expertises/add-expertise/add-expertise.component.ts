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
import {UserService} from "../../../services/user.service";
import {AuthenticationService} from "../../../services/authentication.service";
import {PopupService} from "../../../services/popup.service";
import {PopupType} from "../../../enums/popup-type";

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

  constructor(private popupService: PopupService, private authService: AuthenticationService, private userService: UserService, private router: Router, private activatedRoute: ActivatedRoute, private claimService: ClaimService, private expertiseService: ExpertiseService) {
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

  onFileSelected(event: any){
    this.selectedFile = event.target.files[0];
  }

  addExpertise() {
    this.loading = true;
    this.expertiseService.addExpertise(this.expertise, this.selectedFile).subscribe({
      next: (expertise) => {
        this.popupService.show("Expertise added", PopupType.INFO)

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
