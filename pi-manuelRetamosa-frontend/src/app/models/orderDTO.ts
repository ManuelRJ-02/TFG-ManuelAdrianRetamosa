import { DetailOrderDTO } from './detailOrderDTO';

export interface OrderDTO {
  id:            number;
  paymentMethod: string;
  total:         number;
  detailOrders:  DetailOrderDTO[];
  userId:        number;
}
