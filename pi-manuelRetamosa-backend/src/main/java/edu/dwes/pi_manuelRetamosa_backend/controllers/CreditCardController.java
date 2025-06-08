/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.dwes.pi_manuelRetamosa_backend.controllers;


import edu.dwes.pi_manuelRetamosa_backend.models.DTOs.CreditCardDTO;
import edu.dwes.pi_manuelRetamosa_backend.services.CreditCardService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author manue
 */
@RestController
@RequestMapping("/creditCards")
public class CreditCardController {
    
    @Autowired
    private CreditCardService creditCardService;
    
    @GetMapping
    public ResponseEntity<List< CreditCardDTO>> findAll(){
        return ResponseEntity.ok(creditCardService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        try{
             CreditCardDTO  creditCard=creditCardService.findById(id);
            return ResponseEntity.ok( creditCard);
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    @PostMapping("/crear")
    public ResponseEntity<?> create(@Valid @RequestBody CreditCardDTO creditCard, BindingResult result){
        if (result.hasErrors()) {
            return validacion(result);
        }
        CreditCardDTO newCreditCard=creditCardService.save(creditCard);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCreditCard);
    }
    
    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        try{
            creditCardService.delete(id);
            return ResponseEntity.noContent().build();
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CreditCardDTO>> getByUser(@PathVariable Long userId) {
        List<CreditCardDTO> list = creditCardService.findByUser(userId);
        return ResponseEntity.ok(list);
    }
    
    private static final List<String> ORDER = List.of("holder","cardNumber","expirationMonth","expirationYear","securityCode");

    private ResponseEntity<Map<String, Object>> validacion(BindingResult result) {
        List<String> errores = result.getFieldErrors().stream()
            .sorted(Comparator.comparingInt(err -> ORDER.indexOf(err.getField())))
            .map(FieldError::getDefaultMessage)
            .toList();

        return ResponseEntity.badRequest().body(Map.of("errors", errores));
    }
}
