import {User} from "./user";
import {Term} from "./term";

export interface Insurance {
  id: number;
  name: string;
  type: string;
  coverageAmount: number;
  insurer: User
  terms: Term[];
}
