/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.dwes.pi_manuelRetamosa_backend.models.DTOs;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 *
 * @author manue
 */
public class ProductVariantDTO {
    
    private Long id;
    
    @NotBlank
    private String productVariantSize;

    @NotBlank
    private String color;

    @DecimalMin(value = "0.0", inclusive = false)
    private float price;

    @Min(0)
    private Long stock;

    @NotBlank
    private String productVariantImage;

    private boolean available;
    
    private Long productId;
    
    @NotBlank
    @Size(max = 100)
    private String productName;

    @NotBlank
    @Size(max = 1000)
    private String productDescription;  

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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
}
