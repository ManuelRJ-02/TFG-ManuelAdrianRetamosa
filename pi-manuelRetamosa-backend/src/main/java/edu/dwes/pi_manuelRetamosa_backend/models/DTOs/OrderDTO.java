/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.dwes.pi_manuelRetamosa_backend.models.DTOs;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

/**
 *
 * @author manue
 */
public class OrderDTO {
    
    private Long id;

    @NotBlank
    private String paymentMethod;

    @DecimalMin(value = "0.0", inclusive = true)
    private float total;
    
    private List<DetailOrderDTO> detailOrders;
    
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public List<DetailOrderDTO> getDetailOrders() {
        return detailOrders;
    }

    public void setDetailOrders(List<DetailOrderDTO> detailOrders) {
        this.detailOrders = detailOrders;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    

}
