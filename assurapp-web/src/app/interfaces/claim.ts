import { ClaimStatus } from "./claim-status.enum";
import { Expertise } from "./expertise";

export interface Claim {
  id: number;
  description: string;
  date: Date;
  status: ClaimStatus;
  expertise?: Number;
}
