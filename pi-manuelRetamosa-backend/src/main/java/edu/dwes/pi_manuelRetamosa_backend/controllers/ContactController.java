/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.dwes.pi_manuelRetamosa_backend.controllers;

import edu.dwes.pi_manuelRetamosa_backend.models.DTOs.ContactDTO;
import edu.dwes.pi_manuelRetamosa_backend.services.ContactService;
import jakarta.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author manue
 */
@RestController
@RequestMapping("/contact")
public class ContactController {
    
    private final ContactService contactService;
    private static final String DESTINATION = "manuelretamosajaenes@gmail.com";

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping("/sendMail")
    public ResponseEntity<?> sendContact(@Valid @RequestBody ContactDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            return validacion(result);
        }
        String subject = "Nuevo mensaje de " + dto.getName();
        String body    = "De: " + dto.getEmail() + "\n\n" + dto.getMessage();
        contactService.sendSimpleMessage(DESTINATION, subject, body);
        return ResponseEntity.ok(Map.of("message", "Mensaje enviado con éxito"));
    }
    
    private static final List<String> ORDER = List.of("name", "email", "message");
    
    private ResponseEntity<Map<String, Object>> validacion(BindingResult result) {
        List<String> errores = result.getFieldErrors().stream()
            .sorted(Comparator.comparingInt(err -> ORDER.indexOf(err.getField())))
            .map(FieldError::getDefaultMessage)
            .toList();

        return ResponseEntity.badRequest().body(Map.of("errors", errores));
    }
}
