/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.dwes.pi_manuelRetamosa_backend.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Objects;

/**
 *
 * @author manue
 */
@Entity
@Table(name = "songs")
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(name = "year_publication", nullable = false)
    private Long yearPublication;
    
    @Column(name = "cover_url", nullable = false)
    private String coverUrl;
    
    @Column(nullable = false)
    private String url;
    
    @Column(nullable = false)
    private String duration;
    
    @Column(name = "track_number", nullable = true)
    private Long trackNumber;
    
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id", nullable = true)
    private Album album;

    public Song(Long id, String title, Long yearPublication, String coverUrl, String url, String duration, Long trackNumber, Album album) {
        this.id = id;
        this.title = title;
        this.yearPublication = yearPublication;
        this.coverUrl = coverUrl;
        this.url = url;
        this.duration = duration;
        this.trackNumber = trackNumber;
        this.album = album;
    }

    public Song(Long id) {
        this.id = id;
    }

    public Song() {
    }

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

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Song other = (Song) obj;
        return Objects.equals(this.id, other.id);
    }
    
    
}
