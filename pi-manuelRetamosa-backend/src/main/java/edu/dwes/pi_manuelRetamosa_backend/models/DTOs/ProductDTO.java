/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.dwes.pi_manuelRetamosa_backend.models.DTOs;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 *
 * @author manue
 */
public class ProductDTO {
    private Long id;
    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres")
    @Pattern(regexp = "$|^[\\p{L}0-9 .,'\"\\-()]+$",message = "El nombre solo puede contener letras, números, espacios y .,'\\\"-()")
    private String productName;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 1000, message = "La descripción no puede tener más de 1000 caracteres")
    @Pattern(regexp = "$|^[\\p{L}0-9 \\n\\r\\.,;:¡!¿?\"'()\\-]+$",message = "La descripción solo puede contener letras, números, espacios, saltos de línea y .,:;¡!¿?\\\"'()-")
    private String productDescription;

    @NotNull(message = "El precio base es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor que 0")
    @Digits(integer = 10, fraction = 2, message = "El precio admite hasta 2 decimales")
    private float priceBase;

    @NotBlank(message = "La imagen es obligatoria")
    private String genericImage;

    @NotBlank(message = "La categoría es obligatoria")
    @Size(max = 50, message = "La categoría no puede tener más de 50 caracteres")
    private String category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    
    public float getPriceBase() {
        return priceBase;
    }

    public void setPriceBase(float priceBase) {
        this.priceBase = priceBase;
    }

    public String getGenericImage() {
        return genericImage;
    }

    public void setGenericImage(String genericImage) {
        this.genericImage = genericImage;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
    
}
