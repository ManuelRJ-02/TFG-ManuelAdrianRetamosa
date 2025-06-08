/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.dwes.pi_manuelRetamosa_backend.controllers;

import edu.dwes.pi_manuelRetamosa_backend.models.DTOs.ConcertDTO;
import edu.dwes.pi_manuelRetamosa_backend.services.ConcertService;
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
@RequestMapping("/concerts")
public class ConcertController {
    
    @Autowired
    private ConcertService concertService;
    
    @GetMapping
    public ResponseEntity<List<ConcertDTO>> findAll(){
        return ResponseEntity.ok(concertService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        try{
            ConcertDTO concert=concertService.findById(id);
            return ResponseEntity.ok(concert);
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ConcertDTO concert, BindingResult result){
        if (result.hasErrors()) {
            return validacion(result);
        }
        ConcertDTO newConcert=concertService.save(concert);
        return ResponseEntity.status(HttpStatus.CREATED).body(newConcert);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        try{
            concertService.delete(id);
            return ResponseEntity.noContent().build();
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id, @Valid @RequestBody ConcertDTO newConcert, BindingResult result){
        try{
            if (result.hasErrors()) {
                return validacion(result);
            }
            ConcertDTO updated = concertService.update(id, newConcert);
            return ResponseEntity.ok(updated);
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    private static final List<String> ORDER = List.of("date","place","urlTicketSale");

    private ResponseEntity<Map<String, Object>> validacion(BindingResult result) {
        List<String> errores = result.getFieldErrors().stream()
            .sorted(Comparator.comparingInt(err -> ORDER.indexOf(err.getField())))
            .map(FieldError::getDefaultMessage)
            .toList();

        return ResponseEntity.badRequest().body(Map.of("errors", errores));
    }
}
