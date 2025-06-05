/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.dwes.pi_manuelRetamosa_backend.services;

import edu.dwes.pi_manuelRetamosa_backend.models.DTOs.CartProductDTO;
import edu.dwes.pi_manuelRetamosa_backend.models.daos.ICartProductRepository;
import edu.dwes.pi_manuelRetamosa_backend.models.daos.ICartShoppingRepository;
import edu.dwes.pi_manuelRetamosa_backend.models.daos.IProductVariantRepository;
import edu.dwes.pi_manuelRetamosa_backend.models.entities.CartProduct;
import edu.dwes.pi_manuelRetamosa_backend.models.entities.CartShopping;
import edu.dwes.pi_manuelRetamosa_backend.models.entities.ProductVariant;
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
public class CartProductService {
    
    @Autowired
    private ICartProductRepository cartProductRepository;
    
    @Autowired
    private IProductVariantRepository productVariantRepository;
    
    @Autowired
    private ICartShoppingRepository cartShoppingRepository;
    
    @Autowired
    private ConverterDTO converterDTO;
    
    public List<CartProductDTO> findAll(){
        List<CartProduct> list =(List<CartProduct>)cartProductRepository.findAll();
        List<CartProductDTO> listDTOs=new ArrayList<>();
        for(CartProduct cp:list){
            listDTOs.add(converterDTO.convADTO(cp));
        }
        
        return listDTOs;
    }
    
    public CartProductDTO findById(Long id){
        CartProduct cp= cartProductRepository.findById(id).orElseThrow(() -> new RuntimeException("Carrito producto no encontrado"));
        return converterDTO.convADTO(cp);
    }
    
    public CartProductDTO save(CartProductDTO dto) {
        Long cartId    = dto.getCartShoppingId();
        Long variantId = dto.getProductVariantId();
        Long amountToAdd = dto.getAmount();
        CartProduct existing = cartProductRepository.findByCartShoppingIdAndProductVariantId(cartId, variantId).orElse(null);
        CartProduct saved;
        if (existing != null) {
            existing.setAmount(existing.getAmount() + amountToAdd);
            existing.setUnitPrice(dto.getUnitPrice());
            saved = cartProductRepository.save(existing);
        } else {
            CartProduct cp = converterDTO.convAEntidad(dto);
            saved = cartProductRepository.save(cp);
        }
        CartShopping cart = saved.getCartShopping();
        recalculateCartTotal(cart);
        return converterDTO.convADTO(saved);
    }
    
    @Transactional
    public void delete(Long id) {
        CartProduct cp = cartProductRepository.findById(id).orElseThrow(() -> new RuntimeException("Carrito producto no encontrado"));
        CartShopping cart = cp.getCartShopping();
        cartProductRepository.delete(cp);
        recalculateCartTotal(cart);
    }
    
    @Transactional
    public void deleteByCartId(Long cartId) {
        cartProductRepository.deleteByCartShoppingId(cartId);
        CartShopping cart = cartShoppingRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Carrito de compra no encontrado"));
        cart.setTotal(0);
        cartShoppingRepository.save(cart);
    }
    
    @Transactional
    public CartProductDTO update(Long id, CartProductDTO dto){
        CartProduct cp= cartProductRepository.findById(id).orElseThrow(() -> new RuntimeException("Carrito producto no encontrado"));
        cp.setAmount(dto.getAmount());
        cp.setUnitPrice(dto.getUnitPrice());     
        
        if (dto.getProductVariantId() != null) {
            ProductVariant productVariant = productVariantRepository.findById(dto.getProductVariantId()).orElseThrow(() -> new RuntimeException("Variante de producto no encontrado"));
            cp.setProductVariant(productVariant);
        } 
        
        if (dto.getCartShoppingId() != null) {
            CartShopping cartShopping = cartShoppingRepository.findById(dto.getCartShoppingId()).orElseThrow(() -> new RuntimeException("Carrito de compra no encontrado"));
            cp.setCartShopping(cartShopping);
        }  
        CartProduct updated = cartProductRepository.save(cp);
        CartShopping cart = updated.getCartShopping();
        recalculateCartTotal(cart);
        return converterDTO.convADTO(updated);
        
    } 
    
    private void recalculateCartTotal(CartShopping cart) {
        CartShopping refreshedCart = cartShoppingRepository.findById(cart.getId()).orElseThrow(() -> new RuntimeException("Carrito de compra no encontrado"));
        float suma = 0.0f;
        if (refreshedCart.getCartProducts() != null) {
            for (CartProduct cp : refreshedCart.getCartProducts()) {
                suma += cp.getUnitPrice() * cp.getAmount();
            }
        }
        refreshedCart.setTotal(suma);
        cartShoppingRepository.save(refreshedCart);
    }
    
        
       

}
