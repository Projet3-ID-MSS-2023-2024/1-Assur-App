import { ClaimStatus } from "../enums/claim-status.enum";
import { Expertise } from "./expertise";

export interface Claim {
  id: number;
  description: string;
  date: Date;
  status: ClaimStatus;
  expertise?: Number;
  client?: Number;
  insurer?: Number;
}
