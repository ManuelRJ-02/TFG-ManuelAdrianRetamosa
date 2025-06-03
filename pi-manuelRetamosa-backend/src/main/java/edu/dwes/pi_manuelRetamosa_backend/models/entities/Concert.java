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
import jakarta.persistence.Table;
import java.util.Objects;

/**
 *
 * @author manue
 */
@Entity
@Table(name = "concerts")
public class Concert {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String date;
    
    @Column(nullable = false)
    private String place;
    
    @Column(name = "url_ticket_sale", nullable = true)
    private String urlTicketSale;

    public Concert(Long id, String date, String place, String urlTicketSale) {
        this.id = id;
        this.date = date;
        this.place = place;
        this.urlTicketSale = urlTicketSale;
    }

    public Concert(Long id) {
        this.id = id;
    }

    public Concert() {
    }

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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.id);
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
        final Concert other = (Concert) obj;
        return Objects.equals(this.id, other.id);
    }
    
    
}
