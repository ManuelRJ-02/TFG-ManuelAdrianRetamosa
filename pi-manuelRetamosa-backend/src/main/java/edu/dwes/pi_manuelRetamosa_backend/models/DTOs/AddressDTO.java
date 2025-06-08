/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.dwes.pi_manuelRetamosa_backend.models.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 *
 * @author manue
 */
public class AddressDTO {
    private Long id;
    
    @NotBlank(message="La calle es obligatoria")
    @Pattern(regexp = "$|^[\\p{L}0-9\\s,\\.\\-\\/]{1,100}$", message = "La calle solo puede contener letras, números, espacios, comas, puntos, guiones o barras (1–100 caracteres)")
    private String street;
    
    @NotBlank(message="La ciudad es obligatoria")
    @Pattern(regexp = "$|^[\\p{L}\\s]{1,50}$",message = "La ciudad solo puede contener letras y espacios (1–50 caracteres)")
    private String city;
    
    @NotBlank(message="La provincia es obligatoria")
    @Pattern(regexp = "$|^[\\p{L}\\s]{1,50}$",message = "La provincia solo puede contener letras y espacios (1–50 caracteres)")
    private String province;
    
    @NotBlank(message="El codigo postal es obligatorio")
    @Pattern(regexp = "$|^\\d{5,6}", message = "El código postal debe tener 5 o 6 dígitos")
    private String postalCode;
    
    @NotBlank(message="El país es obligatorio")
    @Pattern(regexp = "$|^[\\p{L}\\s]{1,50}$",message = "El país solo puede contener letras y espacios (1–50 caracteres)")
    private String country;
    
    @NotBlank(message="El bloque es obligatorio")
    @Pattern(regexp = "$|^\\d{1,2}$",message = "El bloque debe tener 1 o 2 dígitos")
    private String blockNumber;
    
    @NotBlank(message="La escalera es obligatoria")
    @Pattern(regexp = "$|^[A-Za-z0-9]{1,2}$",message = "La escalera debe tener 1 o 2 caracteres alfanuméricos")
    private String ladder;
    
    @NotBlank(message="La puerta es obligatoria")
    @Pattern(regexp = "$|^\\d{1,3}[A-Za-z]?",message = "La puerta debe tener 1–3 dígitos y opcionalmente una letra")
    private String door;
    
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(String blockNumber) {
        this.blockNumber = blockNumber;
    }

    public String getLadder() {
        return ladder;
    }

    public void setLadder(String ladder) {
        this.ladder = ladder;
    }

    public String getDoor() {
        return door;
    }

    public void setDoor(String door) {
        this.door = door;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    
}
