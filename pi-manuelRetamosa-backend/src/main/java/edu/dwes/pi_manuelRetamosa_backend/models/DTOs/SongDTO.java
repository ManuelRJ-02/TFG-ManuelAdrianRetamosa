/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.dwes.pi_manuelRetamosa_backend.models.DTOs;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 *
 * @author manue
 */
public class SongDTO {
    private Long id;
    
    @NotBlank
    @Size(max = 100)
    private String title;
    
    @NotNull
    @Max(4)
    private Long yearPublication;
    
    @NotBlank
    @Size(max = 100)
    private String coverUrl;
    
    @NotBlank
    @Size(max = 100)
    private String url;
    
    @NotBlank
    @Size(max = 10)
    private String type;
    
    @NotBlank
    @Size(max = 10)
    private String duration;
    
    @NotNull
    private Long trackNumber;
    
    private Long albumId;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Long getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(Long trackNumber) {
        this.trackNumber = trackNumber;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }
    
    
    
}
