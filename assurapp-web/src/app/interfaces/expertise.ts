import {Claim} from "./claim";
import {User} from "./user";

export interface Expertise {
  id: number;
  description: string;
  date: Date;
  estimation: number;
  imageFile: string;
  claim: Claim;
  expert: User;
}
