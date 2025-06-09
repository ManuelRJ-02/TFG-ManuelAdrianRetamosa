/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.dwes.pi_manuelRetamosa_backend.models.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.dwes.pi_manuelRetamosa_backend.models.entities.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.List;

/**
 *
 * @author manue
 */
public class UserDTO {
    
    Long id;
    
    @NotBlank(message="El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no puede exceder 50 caracteres")
    @Pattern(regexp = "^$|^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$", message = "El nombre solo puede contener letras y espacios")
    private String userName;
    
    @NotBlank(message="Los apellidos son obligatorios")
    @Size(max = 50, message = "El apellidos no puede exceder 50 caracteres")
    @Pattern(regexp = "^$|^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$", message = "Los apellidos solo pueden contener letras y espacios")
    private String surname;
    
    @NotBlank(message="El email es obligatorio")
    @Size(max = 100, message = "El email no puede exceder 100 caracteres")
    @Email(message = "Formato de correo inválido")
    private String email;
    
    @NotBlank(message="La contraseña es obligatoria")
    @Pattern(regexp = "^$|^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$", message = "La contraseña debe de contener al menos 8 caracteres, una mayúscula, una minúscula y un número")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String userPassword;
    
    private String avatar = "assets/header/user-icon.png";
    
    @Pattern(regexp = "^(?:\\d{9})?$", message = "El teléfono debe tener 9 digitos")
    private String phoneNumber;
    
    private boolean enabled;
    
    private List<RoleDTO> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDTO> roles) {
        this.roles = roles;
    }
}
