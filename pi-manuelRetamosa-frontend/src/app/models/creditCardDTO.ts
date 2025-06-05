export interface CreditCardDTO {
  id?: number;
  holder: string;
  cardNumber: string;
  expirationMonth: string;
  expirationYear: string;
  securityCode: string;
  userId: number;
}
