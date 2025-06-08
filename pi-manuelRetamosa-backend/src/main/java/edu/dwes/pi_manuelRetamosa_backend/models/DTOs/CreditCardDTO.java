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
public class CreditCardDTO {
    
    @NotBlank(message="El titular es obligatorio")
    @Pattern(regexp = "^$|^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$", message = "El titular solo puede contener letras y espacios")
    private String holder;
    
    @NotBlank(message="El número de tarjeta es obligatorio")
    @Pattern(regexp = "^$|^[0-9]{16}$", message = "El número de tarjeta debe tener 16 dígitos")
    private String cardNumber;
    
    @NotBlank(message="El mes es obligatorio")
    private String expirationMonth;
    
    @NotBlank(message="El año es obligatorio")
    private String expirationYear;
    
    @NotBlank(message="El código de seguridad es obligatorio")
    @Pattern(regexp = "^$|^[0-9]{3}$", message = "El código de seguridad debe tener 3 dígitos")
    private String securityCode;
    
    private Long userId;

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpirationMonth() {
        return expirationMonth;
    }

    public void setExpirationMonth(String expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    public String getExpirationYear() {
        return expirationYear;
    }

    public void setExpirationYear(String expirationYear) {
        this.expirationYear = expirationYear;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
}
