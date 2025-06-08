/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.dwes.pi_manuelRetamosa_backend.models.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 *
 * @author manue
 */
public class ContactDTO {
    
    @NotBlank(message="El nombre es obligatorio")
    @Size(max = 50, min=1, message="El nombre debe tener entre 1-50 caracteres")
    @Pattern(regexp = "^$|^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$", message = "El nombre solo puede contener letras y espacios")
    private String name;

    @NotBlank(message="El email es obligatorio")
    @Size(max = 100, message = "El email no puede exceder 100 caracteres")
    @Email(message = "Formato de correo inválido")
    private String email;

    @NotBlank(message="El mensaje es obligatorio")
    @Size(max = 1000, min=1, message=" El mensaje debe tener entre 1-1000 caracteres")
    private String message;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    
    
}
