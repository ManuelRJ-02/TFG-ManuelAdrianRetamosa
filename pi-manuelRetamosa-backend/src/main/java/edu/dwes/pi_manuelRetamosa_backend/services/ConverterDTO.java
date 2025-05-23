/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.dwes.pi_manuelRetamosa_backend.services;

import edu.dwes.pi_manuelRetamosa_backend.models.DTOs.AddressDTO;
import edu.dwes.pi_manuelRetamosa_backend.models.DTOs.CartProductDTO;
import edu.dwes.pi_manuelRetamosa_backend.models.DTOs.CartShoppingDTO;
import edu.dwes.pi_manuelRetamosa_backend.models.DTOs.DetailOrderDTO;
import edu.dwes.pi_manuelRetamosa_backend.models.DTOs.OrderDTO;
import edu.dwes.pi_manuelRetamosa_backend.models.DTOs.ProductDTO;
import edu.dwes.pi_manuelRetamosa_backend.models.DTOs.ProductVariantDTO;
import edu.dwes.pi_manuelRetamosa_backend.models.DTOs.RoleDTO;
import edu.dwes.pi_manuelRetamosa_backend.models.DTOs.UserDTO;
import edu.dwes.pi_manuelRetamosa_backend.models.daos.ICartShoppingRepository;
import edu.dwes.pi_manuelRetamosa_backend.models.daos.IProductRepository;
import edu.dwes.pi_manuelRetamosa_backend.models.daos.IProductVariantRepository;
import edu.dwes.pi_manuelRetamosa_backend.models.daos.IUserRepository;
import edu.dwes.pi_manuelRetamosa_backend.models.entities.Address;
import edu.dwes.pi_manuelRetamosa_backend.models.entities.CartProduct;
import edu.dwes.pi_manuelRetamosa_backend.models.entities.CartShopping;
import edu.dwes.pi_manuelRetamosa_backend.models.entities.DetailOrder;
import edu.dwes.pi_manuelRetamosa_backend.models.entities.Order;
import edu.dwes.pi_manuelRetamosa_backend.models.entities.Product;
import edu.dwes.pi_manuelRetamosa_backend.models.entities.ProductVariant;
import edu.dwes.pi_manuelRetamosa_backend.models.entities.Role;
import edu.dwes.pi_manuelRetamosa_backend.models.entities.User;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author manue
 */
@Service
public class ConverterDTO {
    
    @Autowired
    private IUserRepository userRepository;
    
    @Autowired
    private IProductVariantRepository productVariantRepository;
    
    @Autowired
    private IProductRepository productRepository;
    
    @Autowired
    private ICartShoppingRepository cartShoppingRepository;

    public UserDTO convADTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUserName(user.getUserName());
        dto.setSurname(user.getSurname());
        dto.setEmail(user.getEmail());
        dto.setAvatar(user.getAvatar());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setEnabled(user.isEnabled());
        return dto;
    }
    
    public RoleDTO convADTO(Role role) {
        RoleDTO dto = new RoleDTO();
        dto.setId(role.getId());
        dto.setRoleName(role.getRoleName());
        return dto;
    }
    
    public ProductVariantDTO convADTO(ProductVariant productVariant) {
        ProductVariantDTO dto = new ProductVariantDTO();
        dto.setId(productVariant.getId());
        dto.setProductVariantSize(productVariant.getProductVariantSize());
        dto.setColor(productVariant.getColor());
        dto.setPrice(productVariant.getPrice());
        dto.setStock(productVariant.getStock());
        dto.setProductVariantImage(productVariant.getProductVariantImage());
        dto.setAvailable(productVariant.isAvailable());
        dto.setProductId(productVariant.getProduct().getId());
        dto.setProductName(productVariant.getProduct().getProductName());
        dto.setProductDescription(productVariant.getProduct().getProductDescription());
        return dto;
    }
    
    public ProductDTO convADTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setProductName(product.getProductName());
        dto.setProductDescription(product.getProductDescription());
        dto.setPriceBase(product.getPriceBase());
        dto.setGenericImage(product.getGenericImage());
        dto.setCategory(product.getCategory());
        return dto;
    }
    
    public OrderDTO convADTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setPaymentMethod(order.getPaymentMethod());
        dto.setTotal(order.getTotal());
        List<DetailOrderDTO> detailOrderDTO = new ArrayList<>();
        for (DetailOrder detailOrder : order.getDetailOrders()) {
            detailOrderDTO.add(this.convADTO(detailOrder));
        }
        dto.setDetailOrders(detailOrderDTO);
        dto.setUserId(order.getUser().getId());
        return dto;
    }
    
    public DetailOrderDTO convADTO(DetailOrder detailOrder) {
        DetailOrderDTO dto = new DetailOrderDTO();
        dto.setId(detailOrder.getId());
        dto.setAmount(detailOrder.getAmount());
        dto.setProductVariantId(detailOrder.getProductVariant().getId());
        return dto;
    }
    
    public CartShoppingDTO convADTO(CartShopping cartShopping) {
        CartShoppingDTO dto = new CartShoppingDTO();
        dto.setId(cartShopping.getId());
        dto.setCartShoppingState(cartShopping.getCartShoppingState());
        dto.setTotal(cartShopping.getTotal());
        List<CartProductDTO> cartProductDTO = new ArrayList<>();
        for (CartProduct cartProduct : cartShopping.getCartProducts()) {
            cartProductDTO.add(this.convADTO(cartProduct));
        }
        dto.setCartProducts(cartProductDTO);
        dto.setUserId(cartShopping.getUser().getId());
        return dto;
    }
    
    public CartProductDTO convADTO(CartProduct cartProduct) {
        CartProductDTO dto = new CartProductDTO();
        dto.setId(cartProduct.getId());
        dto.setAmount(cartProduct.getAmount());
        dto.setUnitPrice(cartProduct.getUnitPrice());
        dto.setProductVariantId(cartProduct.getProductVariant().getId());
        dto.setCartShoppingId(cartProduct.getCartShopping().getId());
        return dto;
    }
    
    public AddressDTO convADTO(Address address) {
        AddressDTO dto = new AddressDTO();
        dto.setId(address.getId());
        dto.setStreet(address.getStreet());
        dto.setCity(address.getCity());
        dto.setProvince(address.getProvince());
        dto.setPostalCode(address.getPostalCode());
        dto.setCountry(address.getCountry());
        dto.setBlockNumber(address.getBlockNumber());
        dto.setLadder(address.getLadder());
        dto.setDoor(address.getDoor());
        dto.setUserId(address.getUser().getId());
        return dto;
    }
    
    public User convAEntidad(UserDTO dto) {
        User user = new User();
        user.setUserName(dto.getUserName());
        user.setSurname(dto.getSurname());
        user.setEmail(dto.getEmail());
        user.setAvatar(dto.getAvatar());
        user.setPhoneNumber(dto.getPhoneNumber());
        return user;
    }
        
    public Role convAEntidad(RoleDTO dto) {
        Role role = new Role();
        role.setRoleName(dto.getRoleName());
        return role;
    }
    
    public ProductVariant convAEntidad(ProductVariantDTO dto) {
        ProductVariant productVariant = new ProductVariant();
        productVariant.setProductVariantSize(dto.getProductVariantSize());
        productVariant.setColor(dto.getColor());
        productVariant.setPrice(dto.getPrice());
        productVariant.setStock(dto.getStock());
        productVariant.setProductVariantImage(dto.getProductVariantImage());
        productVariant.setAvailable(dto.isAvailable());
        if (dto.getProductId() != null) {
            Product product = productRepository.findById(dto.getProductId()).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
            productVariant.setProduct(product);
        }  
        return productVariant;
    }
    
    public Product convAEntidad(ProductDTO dto) {
        Product product = new Product();
        product.setProductName(dto.getProductName());
        product.setProductDescription(dto.getProductDescription());
        product.setPriceBase(dto.getPriceBase());
        product.setGenericImage(dto.getGenericImage());
        product.setCategory(dto.getCategory());
        return product;
    }
    
    public Order convAEntidad(OrderDTO dto) {
        Order order = new Order();
        order.setPaymentMethod(dto.getPaymentMethod());
        order.setTotal(dto.getTotal());        
        if (dto.getDetailOrders() != null) {
            List<DetailOrder> detailOrders = new ArrayList<>();
            for (DetailOrderDTO detailOrderDTO : dto.getDetailOrders()) {
                DetailOrder detailOrder = this.convAEntidad(detailOrderDTO);
                detailOrder.setOrder(order);
                detailOrders.add(detailOrder);
            }
            order.setDetailOrders(detailOrders);
        }
        
        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            order.setUser(user);
        } 
        return order;
    }
    
    public DetailOrder convAEntidad(DetailOrderDTO dto) {
        DetailOrder detailOrder = new DetailOrder();
        detailOrder.setAmount(dto.getAmount());
        if (dto.getProductVariantId() != null) {
            ProductVariant productVariant = productVariantRepository.findById(dto.getProductVariantId()).orElseThrow(() -> new RuntimeException("Variante de producto no encontrado"));
            detailOrder.setProductVariant(productVariant);
        }  
        return detailOrder;
    } 
    
    public CartShopping convAEntidad(CartShoppingDTO dto) {
        CartShopping cartShopping = new CartShopping();
        cartShopping.setCartShoppingState(dto.getCartShoppingState());
        cartShopping.setTotal(dto.getTotal());        
        if (dto.getCartProducts() != null) {
            List<CartProduct> cartProducts = new ArrayList<>();
            for (CartProductDTO cartProductDTO : dto.getCartProducts()) {
                CartProduct cartProduct = this.convAEntidad(cartProductDTO);
                cartProduct.setCartShopping(cartShopping);
                cartProducts.add(cartProduct);
            }
            cartShopping.setCartProducts(cartProducts);
        }
        
        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            cartShopping.setUser(user);
        } 
        return cartShopping;
    }
    
    public CartProduct convAEntidad(CartProductDTO dto) {
        CartProduct cartProduct = new CartProduct();
        cartProduct.setAmount(dto.getAmount());
        cartProduct.setUnitPrice(dto.getUnitPrice());     
        
        if (dto.getProductVariantId() != null) {
            ProductVariant productVariant = productVariantRepository.findById(dto.getProductVariantId()).orElseThrow(() -> new RuntimeException("Variante de producto no encontrado"));
            cartProduct.setProductVariant(productVariant);
        } 
        
        if (dto.getCartShoppingId() != null) {
            CartShopping cartShopping = cartShoppingRepository.findById(dto.getCartShoppingId()).orElseThrow(() -> new RuntimeException("Carrito de compra no encontrado"));
            cartProduct.setCartShopping(cartShopping);
        } 
        return cartProduct;
    }
    
    public Address convAEntidad(AddressDTO dto) {
        Address address = new Address();
        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setProvince(dto.getProvince());
        address.setPostalCode(dto.getPostalCode());
        address.setCountry(dto.getCountry());
        address.setBlockNumber(dto.getBlockNumber());
        address.setLadder(dto.getLadder());
        address.setDoor(dto.getDoor());     
        
        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            address.setUser(user);
        } 
        return address;
    }

}
