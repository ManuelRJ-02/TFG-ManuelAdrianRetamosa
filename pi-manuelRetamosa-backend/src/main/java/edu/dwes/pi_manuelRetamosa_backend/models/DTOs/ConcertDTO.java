/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.dwes.pi_manuelRetamosa_backend.models.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 *
 * @author manue
 */
public class ConcertDTO {
    private Long id;
    
    @NotBlank(message="La fecha es obligatoria")
    @Size(max = 4, message="La fecha no puede exceder 4 numeros")
    private String date;
    
    @NotBlank(message="El lugar es obligatorio")
    @Size(max = 100, message="El lugar no puede exceder 100 caracteres")
    @Pattern(regexp= "^$|^[\\p{L} ]+,\\s*[\\p{L} ]+$",message= "Formato de lugar incorrecto. Debes indicar 'Ciudad, País'")
    private String place;
    
    @NotBlank(message="La url del ticket es obligatoria")
    @Size(max = 200, message = "La URL no puede exceder 200 caracteres")
    @Pattern(regexp  = "^$|^https://.*",message = "La URL debe empezar con https://")
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
