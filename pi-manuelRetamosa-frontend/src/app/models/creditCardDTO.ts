export interface CreditCardDTO {
  id?: number;            // <- Opcional, el backend asignará el id al crear
  holder: string;
  cardNumber: string;
  expirationMonth: string;
  expirationYear: string;
  securityCode: string;
  userId: number;         // <- Será el id del usuario actual
}
