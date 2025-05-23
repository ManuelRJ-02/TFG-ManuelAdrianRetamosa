/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.dwes.pi_manuelRetamosa_backend.services;

import edu.dwes.pi_manuelRetamosa_backend.models.DTOs.DetailOrderDTO;
import edu.dwes.pi_manuelRetamosa_backend.models.daos.IDetailOrderRepository;
import edu.dwes.pi_manuelRetamosa_backend.models.daos.IProductVariantRepository;
import edu.dwes.pi_manuelRetamosa_backend.models.entities.DetailOrder;
import edu.dwes.pi_manuelRetamosa_backend.models.entities.ProductVariant;
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
public class DetailOrderService {
    
    @Autowired
    private IDetailOrderRepository detailOrderRepository;
    
    @Autowired
    private IProductVariantRepository productVariantRepository;
    
    @Autowired
    private ConverterDTO converterDTO;
    
    public List<DetailOrderDTO> findAll(){
        List<DetailOrder> list =(List<DetailOrder>)detailOrderRepository.findAll();
        List<DetailOrderDTO> listDTOs=new ArrayList<>();
        for(DetailOrder dor:list){
            listDTOs.add(converterDTO.convADTO(dor));
        }
        
        return listDTOs;
    }
    
    public DetailOrderDTO findById(Long id){
        DetailOrder dor= detailOrderRepository.findById(id).orElseThrow(() -> new RuntimeException("Detalle de pedido no encontrado"));
        return converterDTO.convADTO(dor);
    }
    
    public DetailOrderDTO save(DetailOrderDTO detailOrderDTO){
        DetailOrder detailOrder=converterDTO.convAEntidad(detailOrderDTO);
        DetailOrder saved= detailOrderRepository.save(detailOrder);
        return converterDTO.convADTO(saved);
    }
    
    @Transactional
    public void delete(Long id){
        detailOrderRepository.findById(id).orElseThrow(() -> new RuntimeException("Detalle de pedido no encontrado"));
        detailOrderRepository.deleteById(id);
    }
    
    public DetailOrderDTO update(Long id, DetailOrderDTO dto){
        DetailOrder dor= detailOrderRepository.findById(id).orElseThrow(() -> new RuntimeException("Detalle de pedido no encontrado"));
        dor.setAmount(dto.getAmount());
        if (dto.getProductVariantId() != null) {
            ProductVariant productVariant = productVariantRepository.findById(dto.getProductVariantId()).orElseThrow(() -> new RuntimeException("Variante de producto no encontrado"));
            dor.setProductVariant(productVariant);
        }   
        DetailOrder updated = detailOrderRepository.save(dor);
        return converterDTO.convADTO(updated);
        
    } 
}
