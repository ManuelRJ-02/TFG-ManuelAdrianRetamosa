/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.dwes.pi_manuelRetamosa_backend.controllers;

import edu.dwes.pi_manuelRetamosa_backend.models.DTOs.SongDTO;
import edu.dwes.pi_manuelRetamosa_backend.services.SongService;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author manue
 */
@RestController
@RequestMapping("/songs")
public class SongController {
    
    @Autowired
    private SongService songService;
    
    @GetMapping
    public ResponseEntity<List<SongDTO>> findAll(){
        return ResponseEntity.ok(songService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        try{
            SongDTO song=songService.findById(id);
            return ResponseEntity.ok(song);
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    @PostMapping("/crear")
    public ResponseEntity<?> create(@Valid @RequestBody SongDTO song, BindingResult result){
        if (result.hasErrors()) {
            return validacion(result);
        }
        SongDTO newSong=songService.save(song);
        return ResponseEntity.status(HttpStatus.CREATED).body(newSong);
    }
    
    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        try{
            songService.delete(id);
            return ResponseEntity.noContent().build();
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    @PutMapping("/editar/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id, @Valid @RequestBody SongDTO newSong, BindingResult result){
        try{
            if (result.hasErrors()) {
                return validacion(result);
            }
            SongDTO updated = songService.update(id, newSong);
            return ResponseEntity.ok(updated);
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    @PostMapping(value = "/uploadCover", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> uploadCover(@RequestParam("file") MultipartFile file) {
        try {
            String url = songService.uploadCover(file);
            return ResponseEntity.ok(url);
        } catch (IllegalArgumentException iae) {
            return ResponseEntity.badRequest().body(iae.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error subiendo la imagen");
        }
    }
    
    private static final List<String> ORDER = List.of("title","yearPublication","coverUrl","url","type","duration","trackNumber");

    private ResponseEntity<Map<String, Object>> validacion(BindingResult result) {
        List<String> errores = result.getFieldErrors().stream()
            .sorted(Comparator.comparingInt(err -> ORDER.indexOf(err.getField())))
            .map(FieldError::getDefaultMessage)
            .toList();

        return ResponseEntity.badRequest().body(Map.of("errors", errores));
    }
}
