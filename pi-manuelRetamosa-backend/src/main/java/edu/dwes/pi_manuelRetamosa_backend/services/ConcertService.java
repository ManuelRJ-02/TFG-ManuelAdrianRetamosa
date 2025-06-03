/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.dwes.pi_manuelRetamosa_backend.services;

import edu.dwes.pi_manuelRetamosa_backend.models.DTOs.ConcertDTO;
import edu.dwes.pi_manuelRetamosa_backend.models.daos.IConcertRepository;
import edu.dwes.pi_manuelRetamosa_backend.models.entities.Concert;
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
public class ConcertService {
    @Autowired
    private IConcertRepository concertRepository;
    
    @Autowired
    private ConverterDTO converterDTO;
    
    public List<ConcertDTO> findAll(){
        List<Concert> list =(List<Concert>)concertRepository.findAll();
        List<ConcertDTO> listDTOs=new ArrayList<>();
        for(Concert c:list){
            listDTOs.add(converterDTO.convADTO(c));
        }
        
        return listDTOs;
    }
    
    public ConcertDTO findById(Long id){
        Concert c= concertRepository.findById(id).orElseThrow(() -> new RuntimeException("Concierto no encontrado"));
        return converterDTO.convADTO(c);
    }
    
    public ConcertDTO save(ConcertDTO concertDTO){
        Concert concert=converterDTO.convAEntidad(concertDTO);
        Concert saved= concertRepository.save(concert);
        return converterDTO.convADTO(saved);
    }
    
    @Transactional
    public void delete(Long id){
        concertRepository.findById(id).orElseThrow(() -> new RuntimeException("Concierto no encontrado"));
        concertRepository.deleteById(id);
    }
    
    public ConcertDTO update(Long id, ConcertDTO dto){
        Concert c= concertRepository.findById(id).orElseThrow(() -> new RuntimeException("Concierto no encontrado"));
        c.setDate(dto.getDate());
        c.setPlace(dto.getPlace());
        c.setUrlTicketSale(dto.getUrlTicketSale());
        Concert updated = concertRepository.save(c);
        return converterDTO.convADTO(updated);
        
    } 
    
}
