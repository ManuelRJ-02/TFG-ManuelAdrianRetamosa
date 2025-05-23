/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.dwes.pi_manuelRetamosa_backend.controllers;

import edu.dwes.pi_manuelRetamosa_backend.models.DTOs.DetailOrderDTO;
import edu.dwes.pi_manuelRetamosa_backend.services.DetailOrderService;
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
@RequestMapping("/detailOrders")
public class DetailOrderController {
    
    @Autowired
    private DetailOrderService detailOrderService;
    
    @GetMapping
    public ResponseEntity<List<DetailOrderDTO>> findAll(){
        return ResponseEntity.ok(detailOrderService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        try{
            DetailOrderDTO detailOrder=detailOrderService.findById(id);
            return ResponseEntity.ok(detailOrder);
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    @PostMapping("/crear")
    public ResponseEntity<DetailOrderDTO> create(@RequestBody DetailOrderDTO detailOrder){
        DetailOrderDTO newDetailOrder=detailOrderService.save(detailOrder);
        return ResponseEntity.status(HttpStatus.CREATED).body(newDetailOrder);
    }
    
    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        try{
            detailOrderService.delete(id);
            return ResponseEntity.noContent().build();
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    @PutMapping("/editar/{id}")
    public ResponseEntity<DetailOrderDTO> edit(@PathVariable Long id, @RequestBody DetailOrderDTO newDetailOrder){
        try{
            DetailOrderDTO updated = detailOrderService.update(id, newDetailOrder);
            return ResponseEntity.ok(updated);
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
}
