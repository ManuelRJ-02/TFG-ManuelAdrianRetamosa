/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.dwes.pi_manuelRetamosa_backend.services;

import edu.dwes.pi_manuelRetamosa_backend.models.DTOs.CartShoppingDTO;
import edu.dwes.pi_manuelRetamosa_backend.models.daos.ICartShoppingRepository;
import edu.dwes.pi_manuelRetamosa_backend.models.daos.IUserRepository;
import edu.dwes.pi_manuelRetamosa_backend.models.entities.CartShopping;
import edu.dwes.pi_manuelRetamosa_backend.models.entities.User;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author manue
 */
@Service
public class CartShoppingService {
    
    @Autowired
    private ICartShoppingRepository cartShoppingRepository;
    
    @Autowired
    private IUserRepository userRepository;
    
    @Autowired
    private ConverterDTO converterDTO;
    
    public List<CartShoppingDTO> findAll(){
        List<CartShopping> list =(List<CartShopping>)cartShoppingRepository.findAll();
        List<CartShoppingDTO> listDTOs=new ArrayList<>();
        for(CartShopping cs:list){
            listDTOs.add(converterDTO.convADTO(cs));
        }
        
        return listDTOs;
    }
    
    public CartShoppingDTO findById(Long id){
        CartShopping cs= cartShoppingRepository.findById(id).orElseThrow(() -> new RuntimeException("Carrito de compra no encontrado"));
        return converterDTO.convADTO(cs);
    }
    
    public CartShoppingDTO findOpenCartByUser(Long userId) {
        CartShopping cart = cartShoppingRepository.findByUserIdAndCartShoppingState(userId, "OPEN").orElseThrow(() -> new RuntimeException("No hay carrito abierto para usuario " + userId));
        return converterDTO.convADTO(cart);
  }
    
//    public List<CartShoppingDTO> findAllByUser(Long userId) {
//        return cartShoppingRepository.findByUserId(userId).stream()
//                   .map(converterDTO::convADTO)
//                   .toList();
//      }
    
    public List<CartShoppingDTO> findAllByUser(Long userId) {
        List<CartShopping> list = cartShoppingRepository.findByUserId(userId);
        List<CartShoppingDTO> listDTOs = new ArrayList<>();
        for (CartShopping cs : list) {
            listDTOs.add(converterDTO.convADTO(cs));
        }
        return listDTOs;
}
    
    public CartShoppingDTO save(CartShoppingDTO cartShoppingDTO){
        CartShopping cartShopping=converterDTO.convAEntidad(cartShoppingDTO);
        CartShopping saved= cartShoppingRepository.save(cartShopping);
        return converterDTO.convADTO(saved);
    }
    
    @Transactional
    public void delete(Long id){
        cartShoppingRepository.findById(id).orElseThrow(() -> new RuntimeException("Carrito de compra no encontrado"));
        cartShoppingRepository.deleteById(id);
    }
    
    public CartShoppingDTO update(Long id, CartShoppingDTO dto){
        CartShopping cs= cartShoppingRepository.findById(id).orElseThrow(() -> new RuntimeException("Carrito de compra encontrado"));
        cs.setCartShoppingState(dto.getCartShoppingState());
        cs.setTotal(dto.getTotal());        
        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            cs.setUser(user);
        } 
        CartShopping updated = cartShoppingRepository.save(cs);
        return converterDTO.convADTO(updated);
        
    } 
}
