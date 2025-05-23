/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.dwes.pi_manuelRetamosa_backend.controllers;

import edu.dwes.pi_manuelRetamosa_backend.models.DTOs.LoginDTO;
import edu.dwes.pi_manuelRetamosa_backend.models.DTOs.UserDTO;
import edu.dwes.pi_manuelRetamosa_backend.services.UserService;
import jakarta.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author manue
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.findById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDTO userDTO, BindingResult result) {
        if (userService.existsByEmail(userDTO.getEmail())) {
            result.rejectValue("email", "Duplicate", "Ya hay un usuario con ese correo");
        }

        if (result.hasErrors()) {
            return validacion(result);
        }

        try {
            UserDTO nuevoUsuario = userService.save(userDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO, BindingResult result) {
        if (result.hasErrors()) {
            return validacion(result);
        }

        try {
            UserDTO dto = userService.login(loginDTO.getEmail(), loginDTO.getUserPassword());
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/crear")
    public ResponseEntity<?> createByAdmin(@Valid @RequestBody UserDTO userDTO, BindingResult result) {
        return register(userDTO, result);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO, BindingResult result) {
        if (result.hasErrors()) return validacion(result);

        try {
            return ResponseEntity.ok(userService.update(id, userDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }

    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            userService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }

    private static final List<String> ORDER = List.of("userName", "surname", "email", "phoneNumber", "userPassword");

    private ResponseEntity<Map<String, Object>> validacion(BindingResult result) {
        List<String> errores = result.getFieldErrors().stream()
            .sorted(Comparator.comparingInt(err -> ORDER.indexOf(err.getField())))
            .map(FieldError::getDefaultMessage)
            .toList();

        return ResponseEntity.badRequest().body(Map.of("errors", errores));
    }
}