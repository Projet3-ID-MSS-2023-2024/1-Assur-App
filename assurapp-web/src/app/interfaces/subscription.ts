import {User} from "./user";
import {Insurance} from "./insurance";
import {Claim} from "./claim";
import {Payment} from "./payment";

export interface Subscription {
  id: number;
  startDate: Date;
  endDate: Date;
  payment: boolean;
  client: User;
  insurance: Insurance;
  claims: Claim[];
  payments: Payment[];
}
