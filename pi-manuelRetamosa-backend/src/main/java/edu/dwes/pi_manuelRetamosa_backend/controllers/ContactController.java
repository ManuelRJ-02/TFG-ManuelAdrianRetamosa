/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.dwes.pi_manuelRetamosa_backend.controllers;

import edu.dwes.pi_manuelRetamosa_backend.models.DTOs.ContactDTO;
import edu.dwes.pi_manuelRetamosa_backend.services.ContactService;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Map<String,String>> sendContact(@Valid @RequestBody ContactDTO dto) {
        String subject = "Nuevo mensaje de " + dto.getName();
        String body    = "De: " + dto.getEmail() + "\n\n" + dto.getMessage();
        contactService.sendSimpleMessage(DESTINATION, subject, body);
        return ResponseEntity.ok(Map.of("message", "Mensaje enviado con éxito"));
    }
}
