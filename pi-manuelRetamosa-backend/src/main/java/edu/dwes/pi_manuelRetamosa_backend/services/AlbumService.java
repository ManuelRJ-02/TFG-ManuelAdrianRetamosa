/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.dwes.pi_manuelRetamosa_backend.services;

import edu.dwes.pi_manuelRetamosa_backend.models.DTOs.AlbumDTO;
import edu.dwes.pi_manuelRetamosa_backend.models.daos.IAlbumRepository;
import edu.dwes.pi_manuelRetamosa_backend.models.entities.Album;
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
public class AlbumService {
    @Autowired
    private IAlbumRepository albumRepository;
    
    @Autowired
    private ConverterDTO converterDTO;
    
    public List<AlbumDTO> findAll(){
        List<Album> list =(List<Album>)albumRepository.findAll();
        List<AlbumDTO> listDTOs=new ArrayList<>();
        for(Album a:list){
            listDTOs.add(converterDTO.convADTO(a));
        }
        
        return listDTOs;
    }
    
    public AlbumDTO findById(Long id){
        Album a= albumRepository.findById(id).orElseThrow(() -> new RuntimeException("Álbum no encontrado"));
        return converterDTO.convADTO(a);
    }
    
    public AlbumDTO save(AlbumDTO albumDTO){
        Album album=converterDTO.convAEntidad(albumDTO);
        Album saved= albumRepository.save(album);
        return converterDTO.convADTO(saved);
    }
    
    @Transactional
    public void delete(Long id){
        albumRepository.findById(id).orElseThrow(() -> new RuntimeException("Álbum no encontrado"));
        albumRepository.deleteById(id);
    }
    
    public AlbumDTO update(Long id, AlbumDTO dto){
        Album a= albumRepository.findById(id).orElseThrow(() -> new RuntimeException("Álbum no encontrado"));
        a.setTitle(dto.getTitle());
        a.setYearPublication(dto.getYearPublication());
        a.setCoverUrl(dto.getCoverUrl());
        a.setUrl(dto.getUrl());
        Album updated = albumRepository.save(a);
        return converterDTO.convADTO(updated);
        
    } 
    
}
