import {PaymentStatus} from "../enums/payment-status";

export interface Payment {
  id: number;
  amount: number;
  transactionDate: Date;
  status: PaymentStatus;
}
