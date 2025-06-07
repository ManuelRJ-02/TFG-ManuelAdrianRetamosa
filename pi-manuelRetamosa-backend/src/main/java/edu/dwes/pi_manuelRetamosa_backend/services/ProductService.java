/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.dwes.pi_manuelRetamosa_backend.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import edu.dwes.pi_manuelRetamosa_backend.models.DTOs.ProductDTO;
import edu.dwes.pi_manuelRetamosa_backend.models.daos.IProductRepository;
import edu.dwes.pi_manuelRetamosa_backend.models.entities.Product;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author manue
 */
@Service
public class ProductService {
    
    @Autowired
    private IProductRepository productRepository;
    
    @Autowired
    private Cloudinary cloudinary;
    
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
    
    public String uploadGenericImage(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("El fichero está vacío");
        }
        try {
            String original = StringUtils.cleanPath(file.getOriginalFilename());
            String ext = "";
            int dot = original.lastIndexOf('.');
            if (dot > 0) 
                ext = original.substring(dot);
            String publicId = "products/" + UUID.randomUUID();
            @SuppressWarnings("unchecked")
            Map<String,Object> res = cloudinary.uploader()
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
