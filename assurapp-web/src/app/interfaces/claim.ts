import { ClaimStatus } from "../enums/claim-status.enum";
import { Expertise } from "./expertise";
import {Subscription} from "rxjs";
import {User} from "./user";

export interface Claim {
  id: number;
  description: string;
  date: Date;
  status: ClaimStatus;
  client: User;
}
