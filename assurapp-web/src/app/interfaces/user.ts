import {Role} from "./role";

export interface User {
  id:number;
  name:string;
  lastname:string;
  email:string;
  password:string;
  role?: Role;
}
