/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.dwes.pi_manuelRetamosa_backend.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import edu.dwes.pi_manuelRetamosa_backend.models.DTOs.AlbumDTO;
import edu.dwes.pi_manuelRetamosa_backend.models.daos.IAlbumRepository;
import edu.dwes.pi_manuelRetamosa_backend.models.entities.Album;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author manue
 */
@Service
public class AlbumService {
    @Autowired
    private IAlbumRepository albumRepository;
    
    @Autowired
    private Cloudinary cloudinary;
     
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
    
    public String uploadCover(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("El fichero está vacío");
        }
        try {
            String original = StringUtils.cleanPath(file.getOriginalFilename());
            String ext = "";
            int dot = original.lastIndexOf('.');
            if (dot > 0) ext = original.substring(dot);

            String publicId = "music/albums/" + UUID.randomUUID();
            @SuppressWarnings("unchecked")
            Map<String, Object> res = cloudinary.uploader()
                .upload(file.getBytes(),
                        ObjectUtils.asMap(
                          "public_id", publicId,
                          "resource_type", "image"
                        ));

            return res.get("secure_url").toString();
        } catch (IOException e) {
            throw new RuntimeException("Error subiendo la imagen a Cloudinary", e);
        }
    }
    
}
