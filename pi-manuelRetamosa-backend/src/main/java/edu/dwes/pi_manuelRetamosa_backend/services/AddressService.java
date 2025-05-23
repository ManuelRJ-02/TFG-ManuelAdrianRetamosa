/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.dwes.pi_manuelRetamosa_backend.services;

import edu.dwes.pi_manuelRetamosa_backend.models.DTOs.AddressDTO;
import edu.dwes.pi_manuelRetamosa_backend.models.daos.IAddressRepository;
import edu.dwes.pi_manuelRetamosa_backend.models.daos.IUserRepository;
import edu.dwes.pi_manuelRetamosa_backend.models.entities.Address;
import edu.dwes.pi_manuelRetamosa_backend.models.entities.User;
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
public class AddressService {
    
    @Autowired
    private IAddressRepository addressRepository;
    
    @Autowired
    private IUserRepository userRepository;
    
    @Autowired
    private ConverterDTO converterDTO;
    
    public List<AddressDTO> findAll(){
        List<Address> list =(List<Address>)addressRepository.findAll();
        List<AddressDTO> listDTOs=new ArrayList<>();
        for(Address a:list){
            listDTOs.add(converterDTO.convADTO(a));
        }
        
        return listDTOs;
    }
    
    public AddressDTO findById(Long id){
        Address a= addressRepository.findById(id).orElseThrow(() -> new RuntimeException("Direccion no encontrado"));
        return converterDTO.convADTO(a);
    }
    
    public AddressDTO save(AddressDTO addressDTO){
        Address address=converterDTO.convAEntidad(addressDTO);
        Address saved= addressRepository.save(address);
        return converterDTO.convADTO(saved);
    }
    
    @Transactional
    public void delete(Long id){
        addressRepository.findById(id).orElseThrow(() -> new RuntimeException("Direccion no encontrado"));
        addressRepository.deleteById(id);
    }
    
    public AddressDTO update(Long id, AddressDTO dto){
        Address a= addressRepository.findById(id).orElseThrow(() -> new RuntimeException("Direccion no encontrado"));
        a.setStreet(dto.getStreet());
        a.setCity(dto.getCity());
        a.setProvince(dto.getProvince());
        a.setPostalCode(dto.getPostalCode());
        a.setCountry(dto.getCountry());
        a.setBlockNumber(dto.getBlockNumber());
        a.setLadder(dto.getLadder());
        a.setDoor(dto.getDoor());     
        
        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            a.setUser(user);
        } 
        Address updated = addressRepository.save(a);
        return converterDTO.convADTO(updated);
        
    } 
}
