/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.dwes.pi_manuelRetamosa_backend.models.DTOs;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 *
 * @author manue
 */
public class ProductDTO {
    private Long id;
    
    @NotBlank
    @Size(max = 100)
    private String productName;

    @NotBlank
    @Size(max = 1000)
    private String productDescription;

    @DecimalMin(value = "0.0", inclusive = false)
    private float priceBase;

    @NotBlank
    private String genericImage;

    @NotBlank
    @Size(max = 50)
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
