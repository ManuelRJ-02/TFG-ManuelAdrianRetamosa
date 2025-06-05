/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.dwes.pi_manuelRetamosa_backend.services;

import edu.dwes.pi_manuelRetamosa_backend.models.DTOs.OrderDTO;
import edu.dwes.pi_manuelRetamosa_backend.models.daos.ICartShoppingRepository;
import edu.dwes.pi_manuelRetamosa_backend.models.daos.IDetailOrderRepository;
import edu.dwes.pi_manuelRetamosa_backend.models.daos.IOrderRepository;
import edu.dwes.pi_manuelRetamosa_backend.models.daos.IUserRepository;
import edu.dwes.pi_manuelRetamosa_backend.models.entities.CartProduct;
import edu.dwes.pi_manuelRetamosa_backend.models.entities.CartShopping;
import edu.dwes.pi_manuelRetamosa_backend.models.entities.DetailOrder;
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
    private ICartShoppingRepository cartShoppingRepository;
    
    @Autowired
    private IDetailOrderRepository detailOrderRepository;
    
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
    
    @Transactional
    public OrderDTO upsertFromCart(Long cartId) {
        CartShopping cart = cartShoppingRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
        User user = cart.getUser();
        Order existingOrder = orderRepository.findFirstByUserIdOrderByOrderDateDesc(user.getId()).orElse(null);
        if (existingOrder != null) {
            List<DetailOrder> oldDetails = existingOrder.getDetailOrders();
            for (DetailOrder d : oldDetails) {
                detailOrderRepository.delete(d);
            }
            existingOrder.getDetailOrders().clear();
        } else {
            existingOrder = new Order();
            existingOrder.setUser(user);
            existingOrder.setPaymentMethod("PAGO_PENDIENTE");
            existingOrder.setTotal(cart.getTotal());
            existingOrder = orderRepository.save(existingOrder);
        }
        for (CartProduct cp : cart.getCartProducts()) {
            DetailOrder detail = new DetailOrder();
            detail.setOrder(existingOrder);
            detail.setProductVariant(cp.getProductVariant());
            detail.setAmount(cp.getAmount());
            detailOrderRepository.save(detail);
        }
        existingOrder.setTotal(cart.getTotal());
        existingOrder = orderRepository.save(existingOrder);

        return converterDTO.convADTO(existingOrder);
    }

    @Transactional
    public void finalizeFromCart(Long cartId) {
        CartShopping cart = cartShoppingRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
        User user = cart.getUser();
        Order o = orderRepository.findFirstByUserIdOrderByOrderDateDesc(user.getId()).orElseThrow(() -> new RuntimeException("No hay ninguna orden para finalizar"));
        for (DetailOrder d : o.getDetailOrders()) {
            detailOrderRepository.delete(d);
        }
        o.getDetailOrders().clear();
        orderRepository.delete(o);
        cart.getCartProducts().clear();
        cartShoppingRepository.save(cart);
    }
}
