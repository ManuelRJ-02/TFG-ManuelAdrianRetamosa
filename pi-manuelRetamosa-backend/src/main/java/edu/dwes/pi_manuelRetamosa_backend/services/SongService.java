/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.dwes.pi_manuelRetamosa_backend.services;

import edu.dwes.pi_manuelRetamosa_backend.models.DTOs.SongDTO;
import edu.dwes.pi_manuelRetamosa_backend.models.daos.IAlbumRepository;
import edu.dwes.pi_manuelRetamosa_backend.models.daos.ISongRepository;
import edu.dwes.pi_manuelRetamosa_backend.models.entities.Album;
import edu.dwes.pi_manuelRetamosa_backend.models.entities.Song;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author manue
 */
@Service
public class SongService {
    @Autowired
    private ISongRepository songRepository;
    
    @Autowired
    private IAlbumRepository albumRepository;
    
    @Autowired
    private ConverterDTO converterDTO;
    
    public List<SongDTO> findAll(){
        List<Song> list =(List<Song>)songRepository.findAll();
        List<SongDTO> listDTOs=new ArrayList<>();
        for(Song s:list){
            listDTOs.add(converterDTO.convADTO(s));
        }
        
        return listDTOs;
    }
    
    public SongDTO findById(Long id){
        Song s= songRepository.findById(id).orElseThrow(() -> new RuntimeException("Canción no encontrada"));
        return converterDTO.convADTO(s);
    }
    
    public SongDTO save(SongDTO songDTO){
        Song song=converterDTO.convAEntidad(songDTO);
        Song saved= songRepository.save(song);
        return converterDTO.convADTO(saved);
    }
    
    @Transactional
    public void delete(Long id){
        songRepository.findById(id).orElseThrow(() -> new RuntimeException("Canción no encontrada"));
        songRepository.deleteById(id);
    }
    
    public SongDTO update(Long id, SongDTO dto){
        Song s= songRepository.findById(id).orElseThrow(() -> new RuntimeException("Canción no encontrada"));
        s.setTitle(dto.getTitle());
        s.setYearPublication(dto.getYearPublication());
        s.setCoverUrl(dto.getCoverUrl());
        s.setUrl(dto.getUrl());
        s.setType(dto.getType());
        s.setDuration(dto.getDuration());
        s.setTrackNumber(dto.getTrackNumber());
        
        if (dto.getAlbumId() != null) {
            Album album = albumRepository.findById(dto.getAlbumId()).orElseThrow(() -> new RuntimeException("Álbum no encontrado"));
            s.setAlbum(album);
        } 
        Song updated = songRepository.save(s);
        return converterDTO.convADTO(updated);
        
    } 
    
}
