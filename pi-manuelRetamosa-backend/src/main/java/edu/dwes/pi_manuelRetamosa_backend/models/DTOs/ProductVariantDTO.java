/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.dwes.pi_manuelRetamosa_backend.models.DTOs;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 *
 * @author manue
 */
public class ProductVariantDTO {
    
    private Long id;
    
    @NotBlank(message = "La talla es obligatoria")
    private String productVariantSize;

    @NotBlank(message = "El color es obligatorio")
    @Size(max = 30, message = "El color no puede tener más de 30 caracteres")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚÑáéíóúñ ]+$",message = "El color solo puede contener letras y espacios")
    private String color;

    @NotNull(message = "El precio base es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor que 0")
    @Digits(integer = 10, fraction = 2, message = "El precio admite hasta 2 decimales")
    private float price;

    @Min(value = 0, message = "El stock no puede ser negativo")
    @Max(value = 10000, message = "El stock no puede superar 10 000 unidades")
    private Long stock;

    @NotBlank(message = "La imagen es obligatoria")
    private String productVariantImage;

    private boolean available;
    
    private Long productId;

    private String productName;

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
