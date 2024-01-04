import { ClaimStatus } from "./claim-status.enum";
import { Expertise } from "./expertise";

export interface Claim {
  id: number;
  description: string;
  date: Date;
  status: ClaimStatus;
  imageFile: string;
  expertise?: Number;
}
