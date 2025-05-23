/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.dwes.pi_manuelRetamosa_backend.models.DTOs;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 *
 * @author manue
 */
public class CartProductDTO {
    private Long id;
    
    @NotNull
    @Min(0)
    private Long amount;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private float unitPrice;
    
    private Long productVariantId;

    private Long cartShoppingId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Long getProductVariantId() {
        return productVariantId;
    }

    public void setProductVariantId(Long productVariantId) {
        this.productVariantId = productVariantId;
    }

    public Long getCartShoppingId() {
        return cartShoppingId;
    }

    public void setCartShoppingId(Long cartShoppingId) {
        this.cartShoppingId = cartShoppingId;
    }
    
    
}
