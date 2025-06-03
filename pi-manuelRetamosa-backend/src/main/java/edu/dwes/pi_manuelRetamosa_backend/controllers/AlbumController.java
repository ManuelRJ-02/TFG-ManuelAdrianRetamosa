/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.dwes.pi_manuelRetamosa_backend.controllers;

import edu.dwes.pi_manuelRetamosa_backend.models.DTOs.AlbumDTO;
import edu.dwes.pi_manuelRetamosa_backend.services.AlbumService;
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
@RequestMapping("/albums")
public class AlbumController {
    
    @Autowired
    private AlbumService albumService;
    
    @GetMapping
    public ResponseEntity<List<AlbumDTO>> findAll(){
        return ResponseEntity.ok(albumService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        try{
            AlbumDTO album=albumService.findById(id);
            return ResponseEntity.ok(album);
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    @PostMapping("/crear")
    public ResponseEntity<AlbumDTO> create(@RequestBody AlbumDTO album){
        AlbumDTO newAlbum=albumService.save(album);
        return ResponseEntity.status(HttpStatus.CREATED).body(newAlbum);
    }
    
    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        try{
            albumService.delete(id);
            return ResponseEntity.noContent().build();
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    @PutMapping("/editar/{id}")
    public ResponseEntity<AlbumDTO> edit(@PathVariable Long id, @RequestBody AlbumDTO newAlbum){
        try{
            AlbumDTO updated = albumService.update(id, newAlbum);
            return ResponseEntity.ok(updated);
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
