/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.dwes.pi_manuelRetamosa_backend.models.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author manue
 */
@Entity
@Table(name = "product_variants")
public class ProductVariant {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_variant_size")
    @NotBlank
    private String productVariantSize;

    @NotBlank
    private String color;

    @DecimalMin(value = "0.0", inclusive = false)
    private float price;

    @Min(0)
    private Long stock;

    @Column(name = "product_variant_image")
    @NotBlank
    private String productVariantImage;

    private boolean available;
    
    @OneToMany(mappedBy = "productVariant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetailOrder> detailsOrders = new ArrayList<>();
    
    @OneToMany(mappedBy = "productVariant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartProduct> cartProducts = new ArrayList<>();
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    @NotNull
    private Product product;

    public ProductVariant(Long id, String productVariantSize, String color, float price, Long stock, String productVariantImage, boolean available, Product product) {
        this.id = id;
        this.productVariantSize = productVariantSize;
        this.color = color;
        this.price = price;
        this.stock = stock;
        this.productVariantImage = productVariantImage;
        this.available = available;
        this.product = product;
    }

    public ProductVariant(Long id) {
        this.id = id;
    }

    public ProductVariant() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductVariantSize() {
        return productVariantSize;
    }

    public void setProductVariantSize(String productVariantSize) {
        this.productVariantSize = productVariantSize;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public String getProductVariantImage() {
        return productVariantImage;
    }

    public void setProductVariantImage(String productVariantImage) {
        this.productVariantImage = productVariantImage;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public List<DetailOrder> getDetailsOrders() {
        return detailsOrders;
    }

    public void setDetailsOrders(List<DetailOrder> detailsOrders) {
        this.detailsOrders = detailsOrders;
    }

    public List<CartProduct> getCartProducts() {
        return cartProducts;
    }

    public void setCartProducts(List<CartProduct> cartProducts) {
        this.cartProducts = cartProducts;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final ProductVariant other = (ProductVariant) obj;
        return Objects.equals(this.id, other.id);
    }
    
    
    
}
