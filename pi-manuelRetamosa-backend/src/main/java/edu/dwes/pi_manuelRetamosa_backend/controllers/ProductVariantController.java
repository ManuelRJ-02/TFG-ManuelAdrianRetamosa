/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.dwes.pi_manuelRetamosa_backend.controllers;

import edu.dwes.pi_manuelRetamosa_backend.models.DTOs.ProductVariantDTO;
import edu.dwes.pi_manuelRetamosa_backend.services.ProductVariantService;
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
@RequestMapping("/productVariants")
public class ProductVariantController {
    
    @Autowired
    private ProductVariantService productVariantService;
    
    @GetMapping
    public ResponseEntity<List<ProductVariantDTO>> findAll(){
        return ResponseEntity.ok(productVariantService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        try{
            ProductVariantDTO productVariant=productVariantService.findById(id);
            return ResponseEntity.ok(productVariant);
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    @GetMapping("/byProduct/{productId}")
    public ResponseEntity<List<ProductVariantDTO>> findByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(productVariantService.findByProductId(productId));
    }
    
    @PostMapping("/crear")
    public ResponseEntity<ProductVariantDTO> create(@RequestBody ProductVariantDTO productVariant){
        ProductVariantDTO newProductVariant=productVariantService.save(productVariant);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProductVariant);
    }
    
    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        try{
            productVariantService.delete(id);
            return ResponseEntity.noContent().build();
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    @PutMapping("/editar/{id}")
    public ResponseEntity<ProductVariantDTO> edit(@PathVariable Long id, @RequestBody ProductVariantDTO newProductVariant){
        try{
            ProductVariantDTO updated = productVariantService.update(id, newProductVariant);
            return ResponseEntity.ok(updated);
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
}
