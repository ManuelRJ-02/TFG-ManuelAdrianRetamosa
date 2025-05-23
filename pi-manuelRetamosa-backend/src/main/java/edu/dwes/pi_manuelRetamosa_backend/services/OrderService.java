/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.dwes.pi_manuelRetamosa_backend.services;

import edu.dwes.pi_manuelRetamosa_backend.models.DTOs.OrderDTO;
import edu.dwes.pi_manuelRetamosa_backend.models.daos.IOrderRepository;
import edu.dwes.pi_manuelRetamosa_backend.models.daos.IUserRepository;
import edu.dwes.pi_manuelRetamosa_backend.models.entities.Order;
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
public class OrderService {
    
    @Autowired
    private IOrderRepository orderRepository;
    
    @Autowired
    private IUserRepository userRepository;
    
    @Autowired
    private ConverterDTO converterDTO;
    
    public List<OrderDTO> findAll(){
        List<Order> list =(List<Order>)orderRepository.findAll();
        List<OrderDTO> listDTOs=new ArrayList<>();
        for(Order o:list){
            listDTOs.add(converterDTO.convADTO(o));
        }
        
        return listDTOs;
    }
    
    public OrderDTO findById(Long id){
        Order u= orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        return converterDTO.convADTO(u);
    }
    
    public OrderDTO findLatestByUser(Long userId) {
        Order o = orderRepository.findFirstByUserIdOrderByOrderDateDesc(userId).orElseThrow(() -> new RuntimeException("No hay pedidos para el usuario " + userId));
        return converterDTO.convADTO(o);
  }
    
    public OrderDTO save(OrderDTO orderDTO){
        Order order=converterDTO.convAEntidad(orderDTO);
        Order saved= orderRepository.save(order);
        return converterDTO.convADTO(saved);
    }
    
    @Transactional
    public void delete(Long id){
        orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        orderRepository.deleteById(id);
    }
    
    public OrderDTO update(Long id, OrderDTO dto){
        Order o= orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        o.setPaymentMethod(dto.getPaymentMethod());
        o.setTotal(dto.getTotal());        
        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            o.setUser(user);
        } 
        Order updated = orderRepository.save(o);
        return converterDTO.convADTO(updated);
        
    } 
}
