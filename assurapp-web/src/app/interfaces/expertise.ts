import {Claim} from "./claim";

export interface Expertise {
  id: number;
  description: string;
  date: Date;
  estimation: number;
  imageFile: string;
  claim: Claim;
}
