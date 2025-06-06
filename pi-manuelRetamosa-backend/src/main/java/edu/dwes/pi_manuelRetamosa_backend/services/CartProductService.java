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
    
    @Transactional
    public CartProductDTO save(CartProductDTO dto) {
        Long cartId = dto.getCartShoppingId();
        Long variantId = dto.getProductVariantId();
        Long amountToAdd = dto.getAmount();
        ProductVariant variant = productVariantRepository.findById(variantId).orElseThrow(() -> new RuntimeException("Variante de producto no encontrada"));
        if (variant.getStock() < amountToAdd) {
            throw new RuntimeException("No hay suficiente stock para la variante " + variantId);
        }
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
        variant.setStock(variant.getStock() - amountToAdd);
        productVariantRepository.save(variant);
        CartShopping cart = saved.getCartShopping();
        recalculateCartTotal(cart);
        return converterDTO.convADTO(saved);
    }
    
    @Transactional
    public void delete(Long id) {
        CartProduct cp = cartProductRepository.findById(id).orElseThrow(() -> new RuntimeException("Carrito producto no encontrado"));
        ProductVariant variant = cp.getProductVariant();
        variant.setStock(variant.getStock() + cp.getAmount());
        productVariantRepository.save(variant);
        CartShopping cart = cp.getCartShopping();
        cart.getCartProducts().remove(cp);
        cartProductRepository.delete(cp);
        recalculateCartTotal(cart);
    }
    
    @Transactional
    public void deleteByCartId(Long cartId) {
        CartShopping carrito = cartShoppingRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Carrito de compra no encontrado"));
        List<CartProduct> todosLosItems = new ArrayList<>(carrito.getCartProducts());
        for (CartProduct cp : todosLosItems) {
            ProductVariant variante = cp.getProductVariant();
            variante.setStock(variante.getStock() + cp.getAmount());
            productVariantRepository.save(variante);
        }
        cartProductRepository.deleteByCartShoppingId(cartId);
        CartShopping carritoLimpio = cartShoppingRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Carrito de compra no encontrado"));
        carritoLimpio.getCartProducts().clear();
        carritoLimpio.setTotal(0f);
        cartShoppingRepository.save(carritoLimpio);
    }

    @Transactional
    public CartProductDTO update(Long id, CartProductDTO dto) {
        CartProduct cp = cartProductRepository.findById(id).orElseThrow(() -> new RuntimeException("Carrito producto no encontrado"));
        Long nuevaCantidad = dto.getAmount();
        Long antiguaCantidad = cp.getAmount();
        long diferencia = nuevaCantidad - antiguaCantidad;

        ProductVariant variant = cp.getProductVariant();

        if (diferencia > 0) {
            if (variant.getStock() < diferencia) {
                throw new RuntimeException("No hay suficiente stock para aumentar la cantidad de la variante " + variant.getId());
            }
            variant.setStock(variant.getStock() - diferencia);
        } else if (diferencia < 0) {

            variant.setStock(variant.getStock() + (-diferencia));
        }
        productVariantRepository.save(variant);
        cp.setAmount(nuevaCantidad);
        cp.setUnitPrice(dto.getUnitPrice());
        if (dto.getProductVariantId() != null && !dto.getProductVariantId().equals(cp.getProductVariant().getId())) {
            throw new RuntimeException("No está permitido cambiar la variante en un update de carrito");
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
