import { ClaimStatus } from "./claim-status.enum";
import { Expertise } from "./expertise";

export interface Claim {
  id: number;
  name: string;
  description: string;
  date: Date;
  requestedAmount: number;
  status: ClaimStatus;
  // Expertise: Expertise;
}
