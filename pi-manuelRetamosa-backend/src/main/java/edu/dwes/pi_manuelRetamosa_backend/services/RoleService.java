/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.dwes.pi_manuelRetamosa_backend.services;

import edu.dwes.pi_manuelRetamosa_backend.models.DTOs.RoleDTO;
import edu.dwes.pi_manuelRetamosa_backend.models.daos.IRoleRepository;
import edu.dwes.pi_manuelRetamosa_backend.models.entities.Role;
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
public class RoleService {
    
    @Autowired
    private IRoleRepository roleRepository;
    
    @Autowired
    private ConverterDTO converterDTO;
    
    public List<RoleDTO> findAll(){
        List<Role> list =(List<Role>)roleRepository.findAll();
        List<RoleDTO> listDTOs=new ArrayList<>();
        for(Role r:list){
            listDTOs.add(converterDTO.convADTO(r));
        }
        
        return listDTOs;
    }
    
    public RoleDTO findById(Long id){
        Role r= roleRepository.findById(id).orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        return converterDTO.convADTO(r);
    }
    
    public RoleDTO save(RoleDTO roleDTO){
        Role role=converterDTO.convAEntidad(roleDTO);
        Role saved= roleRepository.save(role);
        return converterDTO.convADTO(saved);
    }
    
    @Transactional
    public void delete(Long id){
        roleRepository.findById(id).orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        roleRepository.deleteById(id);
    }
    
    public RoleDTO update(Long id, RoleDTO dto){
        Role r= roleRepository.findById(id).orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        r.setRoleName(dto.getRoleName()); 
        Role updated = roleRepository.save(r);
        return converterDTO.convADTO(updated);
        
    } 
}
