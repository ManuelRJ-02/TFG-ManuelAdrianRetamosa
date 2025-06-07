/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.dwes.pi_manuelRetamosa_backend.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import edu.dwes.pi_manuelRetamosa_backend.models.DTOs.ProductVariantDTO;
import edu.dwes.pi_manuelRetamosa_backend.models.daos.IProductRepository;
import edu.dwes.pi_manuelRetamosa_backend.models.daos.IProductVariantRepository;
import edu.dwes.pi_manuelRetamosa_backend.models.entities.Product;
import edu.dwes.pi_manuelRetamosa_backend.models.entities.ProductVariant;
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
public class ProductVariantService {
    
    @Autowired
    private IProductVariantRepository productVariantRepository;
    
    @Autowired
    private IProductRepository productRepository;
    
    @Autowired
    private Cloudinary cloudinary;
    
    @Autowired
    private ConverterDTO converterDTO;
    
    public List<ProductVariantDTO> findAll(){
        List<ProductVariant> list =(List<ProductVariant>)productVariantRepository.findAll();
        List<ProductVariantDTO> listDTOs=new ArrayList<>();
        for(ProductVariant pv:list){
            listDTOs.add(converterDTO.convADTO(pv));
        }
        
        return listDTOs;
    }
    
//    public List<ProductVariantDTO> findByProductId(Long productId) {
//        return productVariantRepository.findByProductId(productId)
//                   .stream()
//                   .map(converterDTO::convADTO)
//                   .toList();
//    }
    
    public List<ProductVariantDTO> findByProductId(Long productId) {
        List<ProductVariant> list = productVariantRepository.findByProductId(productId);
        List<ProductVariantDTO> listDTOs = new ArrayList<>();
        for (ProductVariant pv : list) {
            listDTOs.add(converterDTO.convADTO(pv));
        }

        return listDTOs;
}
    
    public ProductVariantDTO findById(Long id){
        ProductVariant pv= productVariantRepository.findById(id).orElseThrow(() -> new RuntimeException("Variante de producto no encontrado"));
        return converterDTO.convADTO(pv);
    }
    
    public ProductVariantDTO save(ProductVariantDTO productVariantDTO){
        ProductVariant productVariant=converterDTO.convAEntidad(productVariantDTO);
        ProductVariant saved= productVariantRepository.save(productVariant);
        return converterDTO.convADTO(saved);
    }
    
    @Transactional
    public void delete(Long id){
        productVariantRepository.findById(id).orElseThrow(() -> new RuntimeException("Variante de producto no encontrado"));
        productVariantRepository.deleteById(id);
    }
    
    public ProductVariantDTO update(Long id, ProductVariantDTO dto){
        ProductVariant pv= productVariantRepository.findById(id).orElseThrow(() -> new RuntimeException("Variante de producto no encontrado"));
        pv.setProductVariantSize(dto.getProductVariantSize());
        pv.setColor(dto.getColor());
        pv.setPrice(dto.getPrice());
        pv.setStock(dto.getStock());
        pv.setProductVariantImage(dto.getProductVariantImage());
        pv.setAvailable(dto.isAvailable());
        if (dto.getProductId() != null) {
            Product product = productRepository.findById(dto.getProductId()).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
            pv.setProduct(product);
        }  
        ProductVariant updated = productVariantRepository.save(pv);
        return converterDTO.convADTO(updated);
        
    } 
    
    public String uploadImage(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Fichero vacío");
        }
        try {
            String original = StringUtils.cleanPath(file.getOriginalFilename());
            String ext = "";
            int dot = original.lastIndexOf('.');
            if (dot > 0) 
                ext = original.substring(dot);
            String publicId = "products/variants/" + UUID.randomUUID();
            @SuppressWarnings("unchecked")
            Map<String,Object> res = cloudinary.uploader()
                .upload(file.getBytes(),
                        ObjectUtils.asMap(
                            "public_id", publicId,
                            "resource_type", "image"
                        ));
            return res.get("secure_url").toString();
        } catch (IOException e) {
            throw new RuntimeException("Error subiendo imagen", e);
        }
    }
}
