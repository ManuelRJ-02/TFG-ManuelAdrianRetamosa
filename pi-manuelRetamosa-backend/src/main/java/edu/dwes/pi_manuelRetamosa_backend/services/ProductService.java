/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.dwes.pi_manuelRetamosa_backend.services;

import edu.dwes.pi_manuelRetamosa_backend.models.DTOs.ProductDTO;
import edu.dwes.pi_manuelRetamosa_backend.models.daos.IProductRepository;
import edu.dwes.pi_manuelRetamosa_backend.models.entities.Product;
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
public class ProductService {
    
    @Autowired
    private IProductRepository productRepository;
    
    @Autowired
    private ConverterDTO converterDTO;
    
    public List<ProductDTO> findAll(){
        List<Product> list =(List<Product>)productRepository.findAll();
        List<ProductDTO> listDTOs=new ArrayList<>();
        for(Product p:list){
            listDTOs.add(converterDTO.convADTO(p));
        }
        
        return listDTOs;
    }
    
    public ProductDTO findById(Long id){
        Product p= productRepository.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return converterDTO.convADTO(p);
    }
    
    public ProductDTO save(ProductDTO productDTO){
        Product product=converterDTO.convAEntidad(productDTO);
        Product saved= productRepository.save(product);
        return converterDTO.convADTO(saved);
    }
    
    @Transactional
    public void delete(Long id){
        productRepository.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        productRepository.deleteById(id);
    }
    
    public ProductDTO update(Long id, ProductDTO dto){
        Product p= productRepository.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        p.setProductName(dto.getProductName());
        p.setProductDescription(dto.getProductDescription());
        p.setPriceBase(dto.getPriceBase());
        p.setGenericImage(dto.getGenericImage());
        p.setCategory(dto.getCategory());
        Product updated = productRepository.save(p);
        return converterDTO.convADTO(updated);
        
    } 
}
