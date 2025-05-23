import { CartProductDTO } from './cartProductDTO';

export interface CartShoppingDTO {
  id:               number;
  cartShoppingState: string;
  dateCreation:     string;
  total:            number;
  cartProducts:     CartProductDTO[];
  userId:           number;
}
