/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.dwes.pi_manuelRetamosa_backend.controllers;

import edu.dwes.pi_manuelRetamosa_backend.models.DTOs.OrderDTO;
import edu.dwes.pi_manuelRetamosa_backend.services.OrderService;
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
@RequestMapping("/orders")
public class OrderController {
    
    @Autowired
    private OrderService orderService;
    
    @GetMapping
    public ResponseEntity<List<OrderDTO>> findAll(){
        return ResponseEntity.ok(orderService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        try{
            OrderDTO role=orderService.findById(id);
            return ResponseEntity.ok(role);
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    @GetMapping("/user/{userId}/latest")
    public ResponseEntity<OrderDTO> getLatestByUser(@PathVariable Long userId) {
      try {
        OrderDTO dto = orderService.findLatestByUser(userId);
        return ResponseEntity.ok(dto);
      } catch (RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
      }
    }
    
    @PostMapping("/create/{cartId}")
    public ResponseEntity<OrderDTO> createFromCart(@PathVariable Long cartId) {
        try {
            OrderDTO newOrder = orderService.upsertFromCart(cartId);
            return ResponseEntity.status(HttpStatus.CREATED).body(newOrder);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/finalize/{cartId}")
    public ResponseEntity<Void> finalizeFromCart(@PathVariable Long cartId) {
        try {
            orderService.finalizeFromCart(cartId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
  
    @PostMapping("/crear")
    public ResponseEntity<OrderDTO> create(@RequestBody OrderDTO order){
        OrderDTO newOrder=orderService.save(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(newOrder);
    }
    
    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        try{
            orderService.delete(id);
            return ResponseEntity.noContent().build();
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    @PutMapping("/editar/{id}")
    public ResponseEntity<OrderDTO> edit(@PathVariable Long id, @RequestBody OrderDTO newOrder){
        try{
            OrderDTO updated = orderService.update(id, newOrder);
            return ResponseEntity.ok(updated);
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
}
