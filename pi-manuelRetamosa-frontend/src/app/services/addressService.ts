import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AddressDTO } from '../models/addressDTO';

@Injectable({
  providedIn: 'root',
})
export class AddressService {
  // Ajusta esta URL base según tu configuración de CORS / dominio
  private apiUrl = 'http://localhost:8080/addresses';

  constructor(private http: HttpClient) {}

  /**
   * Devuelve todas las direcciones asociadas a userId
   */
  getAddressesByUser(userId: number): Observable<AddressDTO[]> {
    return this.http.get<AddressDTO[]>(`${this.apiUrl}/user/${userId}`);
  }

  /**
   * Crea una nueva dirección. Debe incluir userId en el body.
   */
  addAddress(address: AddressDTO): Observable<AddressDTO> {
    // El controlador en back-end mapea a POST /addresses/crear
    return this.http.post<AddressDTO>(`${this.apiUrl}/crear`, address);
  }

  // Podrías añadir más métodos: deleteAddress, updateAddress...
}
