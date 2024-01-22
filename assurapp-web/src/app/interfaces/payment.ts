import {PaymentStatus} from "../enums/payment-status";
import {Subscription} from "./subscription";

export interface Payment {
  id: number;
  amount: number;
  transactionDate: Date;
  status: PaymentStatus;
}
