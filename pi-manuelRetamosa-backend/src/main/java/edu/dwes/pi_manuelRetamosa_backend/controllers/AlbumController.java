/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.dwes.pi_manuelRetamosa_backend.controllers;

import edu.dwes.pi_manuelRetamosa_backend.models.DTOs.AlbumDTO;
import edu.dwes.pi_manuelRetamosa_backend.services.AlbumService;
import jakarta.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<?> create(@Valid @RequestBody AlbumDTO album, BindingResult result){
        if (result.hasErrors()) {
            return validacion(result);
        }
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
    public ResponseEntity<?> edit(@PathVariable Long id, @Valid @RequestBody AlbumDTO newAlbum, BindingResult result){
        try{
            if (result.hasErrors()) {
                return validacion(result);
            }
            AlbumDTO updated = albumService.update(id, newAlbum);
            return ResponseEntity.ok(updated);
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    @PostMapping(value = "/uploadCover", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> uploadCover(@RequestParam("file") MultipartFile file) {
        try {
            String url = albumService.uploadCover(file);
            return ResponseEntity.ok(url);
        } catch (IllegalArgumentException iae) {
            return ResponseEntity.badRequest().body(iae.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error subiendo la imagen");
        }
    }
    
    private static final List<String> ORDER = List.of("title","yearPublication","coverUrl","url");

    private ResponseEntity<Map<String, Object>> validacion(BindingResult result) {
        List<String> errores = result.getFieldErrors().stream()
            .sorted(Comparator.comparingInt(err -> ORDER.indexOf(err.getField())))
            .map(FieldError::getDefaultMessage)
            .toList();

        return ResponseEntity.badRequest().body(Map.of("errors", errores));
    }
    
}
