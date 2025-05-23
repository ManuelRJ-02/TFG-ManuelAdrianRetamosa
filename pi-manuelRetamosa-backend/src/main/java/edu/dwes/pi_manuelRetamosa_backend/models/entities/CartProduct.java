/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.dwes.pi_manuelRetamosa_backend.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;

/**
 *
 * @author manue
 */
@Entity
@Table(name = "cart_products")
public class CartProduct {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long amount;

    @Column(name = "unit_price")
    private float unitPrice;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "product_variant_id", nullable = false)
    @NotNull
    private ProductVariant productVariant;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cart_shopping_id", nullable = false)
    @NotNull
    private CartShopping cartShopping;

    public CartProduct(Long id, Long amount, float unitPrice, ProductVariant productVariant, CartShopping cartShopping) {
        this.id = id;
        this.amount = amount;
        this.unitPrice = unitPrice;
        this.productVariant = productVariant;
        this.cartShopping = cartShopping;
    }

    public CartProduct(Long id) {
        this.id = id;
    }

    public CartProduct() {
    }

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

    public ProductVariant getProductVariant() {
        return productVariant;
    }

    public void setProductVariant(ProductVariant productVariant) {
        this.productVariant = productVariant;
    }

    public CartShopping getCartShopping() {
        return cartShopping;
    }

    public void setCartShopping(CartShopping cartShopping) {
        this.cartShopping = cartShopping;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CartProduct other = (CartProduct) obj;
        return Objects.equals(this.id, other.id);
    }
    
    
}
