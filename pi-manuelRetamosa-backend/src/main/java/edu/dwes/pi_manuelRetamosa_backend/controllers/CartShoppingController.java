/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.dwes.pi_manuelRetamosa_backend.controllers;

import edu.dwes.pi_manuelRetamosa_backend.models.DTOs.CartShoppingDTO;
import edu.dwes.pi_manuelRetamosa_backend.services.CartShoppingService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/cartShoppings")
public class CartShoppingController {
    
    @Autowired
    private CartShoppingService cartShoppingService;
    
    @GetMapping
    public ResponseEntity<List<CartShoppingDTO>> findAll(){
        return ResponseEntity.ok(cartShoppingService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        try{
            CartShoppingDTO role=cartShoppingService.findById(id);
            return ResponseEntity.ok(role);
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<CartShoppingDTO> findOpenByUser(@PathVariable Long userId) {
      CartShoppingDTO dto = cartShoppingService.findOpenCartByUser(userId);
      return ResponseEntity.ok(dto);
    }
    
    @PostMapping("/crear")
    public ResponseEntity<CartShoppingDTO> create(@RequestBody CartShoppingDTO cartShopping){
        CartShoppingDTO newCartShopping=cartShoppingService.save(cartShopping);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCartShopping);
    }
    
    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        try{
            cartShoppingService.delete(id);
            return ResponseEntity.noContent().build();
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    @PutMapping("/editar/{id}")
    public ResponseEntity<CartShoppingDTO> edit(@PathVariable Long id, @RequestBody CartShoppingDTO newCartShopping){
        try{
            CartShoppingDTO updated = cartShoppingService.update(id, newCartShopping);
            return ResponseEntity.ok(updated);
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
}
