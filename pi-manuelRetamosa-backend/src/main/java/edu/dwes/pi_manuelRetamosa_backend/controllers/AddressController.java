/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.dwes.pi_manuelRetamosa_backend.controllers;

import edu.dwes.pi_manuelRetamosa_backend.models.DTOs.AddressDTO;
import edu.dwes.pi_manuelRetamosa_backend.services.AddressService;
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
@RequestMapping("/addresses")
public class AddressController {
    
    @Autowired
    private AddressService addressService;
    
    @GetMapping
    public ResponseEntity<List<AddressDTO>> findAll(){
        return ResponseEntity.ok(addressService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        try{
            AddressDTO address=addressService.findById(id);
            return ResponseEntity.ok(address);
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    @PostMapping("/crear")
    public ResponseEntity<?> create(@Valid @RequestBody AddressDTO address, BindingResult result){
        if (result.hasErrors()) {
            return validacion(result);
        }
        AddressDTO newAddress=addressService.save(address);
        return ResponseEntity.status(HttpStatus.CREATED).body(newAddress);
    }
    
    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        try{
            addressService.delete(id);
            return ResponseEntity.noContent().build();
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    @PutMapping("/editar/{id}")
    public ResponseEntity<AddressDTO> edit(@PathVariable Long id, @RequestBody AddressDTO newAddress){
        try{
            AddressDTO updated = addressService.update(id, newAddress);
            return ResponseEntity.ok(updated);
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AddressDTO>> getByUser(@PathVariable Long userId) {
        List<AddressDTO> list = addressService.findByUser(userId);
        return ResponseEntity.ok(list);
    }
    
    private static final List<String> ORDER = List.of("country", "province", "city", "postalCode", "street", "blockNumber", "ladder", "door");

    private ResponseEntity<Map<String, Object>> validacion(BindingResult result) {
        List<String> errores = result.getFieldErrors().stream()
            .sorted(Comparator.comparingInt(err -> ORDER.indexOf(err.getField())))
            .map(FieldError::getDefaultMessage)
            .toList();

        return ResponseEntity.badRequest().body(Map.of("errors", errores));
    }
    
}
