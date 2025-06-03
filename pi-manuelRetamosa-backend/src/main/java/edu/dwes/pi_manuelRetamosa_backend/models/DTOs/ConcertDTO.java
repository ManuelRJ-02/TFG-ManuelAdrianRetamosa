/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.dwes.pi_manuelRetamosa_backend.models.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 *
 * @author manue
 */
public class ConcertDTO {
    private Long id;
    
    @NotBlank
    @Size(max = 30)
    private String date;
    
    @NotBlank
    @Size(max = 100)
    private String place;
    
    @NotBlank
    @Size(max = 100)
    private String urlTicketSale;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getUrlTicketSale() {
        return urlTicketSale;
    }

    public void setUrlTicketSale(String urlTicketSale) {
        this.urlTicketSale = urlTicketSale;
    }
    
    
    
}
