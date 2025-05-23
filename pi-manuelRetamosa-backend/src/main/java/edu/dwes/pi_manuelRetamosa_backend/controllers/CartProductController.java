/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.dwes.pi_manuelRetamosa_backend.controllers;

import edu.dwes.pi_manuelRetamosa_backend.models.DTOs.CartProductDTO;
import edu.dwes.pi_manuelRetamosa_backend.services.CartProductService;
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
@RequestMapping("/cartProducts")
public class CartProductController {
    
    @Autowired
    private CartProductService cartProductService;
    
    @GetMapping
    public ResponseEntity<List<CartProductDTO>> findAll(){
        return ResponseEntity.ok(cartProductService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        try{
            CartProductDTO cartProduct=cartProductService.findById(id);
            return ResponseEntity.ok(cartProduct);
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    @PostMapping("/crear")
    public ResponseEntity<CartProductDTO> create(@RequestBody CartProductDTO cartProduct){
        CartProductDTO newCartProduct=cartProductService.save(cartProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCartProduct);
    }
    
    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        try{
            cartProductService.delete(id);
            return ResponseEntity.noContent().build();
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    @DeleteMapping("/byCart/{cartId}")
    public ResponseEntity<Void> deleteByCart(@PathVariable Long cartId) {
        cartProductService.deleteByCartId(cartId);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/editar/{id}")
    public ResponseEntity<CartProductDTO> edit(@PathVariable Long id, @RequestBody CartProductDTO newCartProduct){
        try{
            CartProductDTO updated = cartProductService.update(id, newCartProduct);
            return ResponseEntity.ok(updated);
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    
}
