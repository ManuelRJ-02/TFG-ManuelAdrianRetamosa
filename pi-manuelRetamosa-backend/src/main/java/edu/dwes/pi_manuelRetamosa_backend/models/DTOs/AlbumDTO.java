/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.dwes.pi_manuelRetamosa_backend.models.DTOs;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.List;

/**
 *
 * @author manue
 */
public class AlbumDTO {
    private Long id;
    
    @NotBlank(message="El título es obligatorio")
    @Size(max=100, message="El título no puede exceder 100 caracteres")
    private String title;
    
    @NotNull(message="El año de publicación es obligatorio")
    @Min(value = 2000, message="El año debe ser ≥ 2000")
    @Max(value = 2100, message="El año debe ser < 2100")
    private Long yearPublication;
    
    @NotBlank(message="La URL de la portada es obligatoria")
    private String coverUrl;
    
    @NotBlank(message="La URL de la canción es obligatoria")
    @Pattern(regexp="$|https?://.*",message="La URL de la canción debe empezar por http:// o https://")
    private String url;
    
    List<SongDTO> songs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getYearPublication() {
        return yearPublication;
    }

    public void setYearPublication(Long yearPublication) {
        this.yearPublication = yearPublication;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<SongDTO> getSongs() {
        return songs;
    }

    public void setSongs(List<SongDTO> songs) {
        this.songs = songs;
    }
    
    
}
