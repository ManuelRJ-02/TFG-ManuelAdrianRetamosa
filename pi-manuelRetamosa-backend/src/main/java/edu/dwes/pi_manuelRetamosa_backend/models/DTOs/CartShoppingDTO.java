/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.dwes.pi_manuelRetamosa_backend.models.DTOs;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;


/**
 *
 * @author manue
 */
public class CartShoppingDTO {
    
    private Long id;
    
    @NotBlank(message = "El estado del carrito no puede estar vacío")
    private String cartShoppingState;

    private LocalDate dateCreation;

    @DecimalMin(value = "0.0", inclusive = true, message = "El total no puede ser negativo")
    private float total;
    
    List<CartProductDTO> cartProducts;
    
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCartShoppingState() {
        return cartShoppingState;
    }

    public void setCartShoppingState(String cartShoppingState) {
        this.cartShoppingState = cartShoppingState;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public List<CartProductDTO> getCartProducts() {
        return cartProducts;
    }

    public void setCartProducts(List<CartProductDTO> cartProducts) {
        this.cartProducts = cartProducts;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    

}
