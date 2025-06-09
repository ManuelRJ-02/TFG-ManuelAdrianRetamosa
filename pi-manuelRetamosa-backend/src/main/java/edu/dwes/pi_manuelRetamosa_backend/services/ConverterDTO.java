/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.dwes.pi_manuelRetamosa_backend.services;

import edu.dwes.pi_manuelRetamosa_backend.models.DTOs.AddressDTO;
import edu.dwes.pi_manuelRetamosa_backend.models.DTOs.AlbumDTO;
import edu.dwes.pi_manuelRetamosa_backend.models.DTOs.CartProductDTO;
import edu.dwes.pi_manuelRetamosa_backend.models.DTOs.CartShoppingDTO;
import edu.dwes.pi_manuelRetamosa_backend.models.DTOs.ConcertDTO;
import edu.dwes.pi_manuelRetamosa_backend.models.DTOs.CreditCardDTO;
import edu.dwes.pi_manuelRetamosa_backend.models.DTOs.DetailOrderDTO;
import edu.dwes.pi_manuelRetamosa_backend.models.DTOs.OrderDTO;
import edu.dwes.pi_manuelRetamosa_backend.models.DTOs.ProductDTO;
import edu.dwes.pi_manuelRetamosa_backend.models.DTOs.ProductVariantDTO;
import edu.dwes.pi_manuelRetamosa_backend.models.DTOs.ProfileDTO;
import edu.dwes.pi_manuelRetamosa_backend.models.DTOs.RoleDTO;
import edu.dwes.pi_manuelRetamosa_backend.models.DTOs.SongDTO;
import edu.dwes.pi_manuelRetamosa_backend.models.DTOs.UserDTO;
import edu.dwes.pi_manuelRetamosa_backend.models.daos.IAlbumRepository;
import edu.dwes.pi_manuelRetamosa_backend.models.daos.ICartShoppingRepository;
import edu.dwes.pi_manuelRetamosa_backend.models.daos.IProductRepository;
import edu.dwes.pi_manuelRetamosa_backend.models.daos.IProductVariantRepository;
import edu.dwes.pi_manuelRetamosa_backend.models.daos.IRoleRepository;
import edu.dwes.pi_manuelRetamosa_backend.models.daos.IUserRepository;
import edu.dwes.pi_manuelRetamosa_backend.models.entities.Address;
import edu.dwes.pi_manuelRetamosa_backend.models.entities.Album;
import edu.dwes.pi_manuelRetamosa_backend.models.entities.CartProduct;
import edu.dwes.pi_manuelRetamosa_backend.models.entities.CartShopping;
import edu.dwes.pi_manuelRetamosa_backend.models.entities.Concert;
import edu.dwes.pi_manuelRetamosa_backend.models.entities.CreditCard;
import edu.dwes.pi_manuelRetamosa_backend.models.entities.DetailOrder;
import edu.dwes.pi_manuelRetamosa_backend.models.entities.Order;
import edu.dwes.pi_manuelRetamosa_backend.models.entities.Product;
import edu.dwes.pi_manuelRetamosa_backend.models.entities.ProductVariant;
import edu.dwes.pi_manuelRetamosa_backend.models.entities.Role;
import edu.dwes.pi_manuelRetamosa_backend.models.entities.Song;
import edu.dwes.pi_manuelRetamosa_backend.models.entities.User;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
    private IRoleRepository roleRepository;
    
    @Autowired
    private ICartShoppingRepository cartShoppingRepository;
    
    @Autowired
    private IAlbumRepository albumRepository;

    public UserDTO convADTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUserName(user.getUserName());
        dto.setSurname(user.getSurname());
        dto.setEmail(user.getEmail());
        dto.setAvatar(user.getAvatar());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setEnabled(user.isEnabled());
        List<RoleDTO> roleDTO = new ArrayList<>();
        for (Role role : user.getRoles()) {
            roleDTO.add(this.convADTO(role));
        }
        dto.setRoles(roleDTO);
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
    
    public ConcertDTO convADTO(Concert concert) {
        ConcertDTO dto = new ConcertDTO();
        dto.setId(concert.getId());
        dto.setDate(concert.getDate());
        dto.setPlace(concert.getPlace());
        dto.setUrlTicketSale(concert.getUrlTicketSale());
        return dto;
    }
    
    public SongDTO convADTO(Song song) {
        SongDTO dto = new SongDTO();
        dto.setId(song.getId());
        dto.setTitle(song.getTitle());
        dto.setYearPublication(song.getYearPublication());
        dto.setCoverUrl(song.getCoverUrl());
        dto.setUrl(song.getUrl());
        dto.setType(song.getType());
        dto.setDuration(song.getDuration());
        dto.setTrackNumber(song.getTrackNumber());
        if (song.getAlbum() != null) {
            dto.setAlbumId(song.getAlbum().getId());
        }
        return dto;
    }
    
    public AlbumDTO convADTO(Album album) {
        AlbumDTO dto = new AlbumDTO();
        dto.setId(album.getId());
        dto.setTitle(album.getTitle());
        dto.setYearPublication(album.getYearPublication());
        dto.setCoverUrl(album.getCoverUrl());
        dto.setUrl(album.getUrl());
        List<SongDTO> songDTO = new ArrayList<>();
        for (Song song : album.getSongs()) {
            songDTO.add(this.convADTO(song));
        }
        dto.setSongs(songDTO);
        return dto;
    }
    
     public CreditCardDTO convADTO(CreditCard card) {
        CreditCardDTO dto = new CreditCardDTO();
        dto.setHolder(card.getHolder());
        dto.setCardNumber(card.getCardNumber());
        dto.setExpirationMonth(card.getExpirationMonth());
        dto.setExpirationYear(card.getExpirationYear());
        dto.setSecurityCode(card.getSecurityCode());
        dto.setUserId(card.getUser().getId());
        return dto;
    }
    
    public User convAEntidad(UserDTO dto) {
        User user = new User();
        user.setUserName(dto.getUserName());
        user.setSurname(dto.getSurname());
        user.setEmail(dto.getEmail());
        user.setAvatar(dto.getAvatar());
        user.setPhoneNumber(dto.getPhoneNumber());

        if (dto.getRoles() != null) {
            List<Role> roles = dto.getRoles().stream()
                .map(roleDTO -> 
                    // buscamos la entidad Role por su nombre
                    roleRepository.findByRoleName(roleDTO.getRoleName())
                        .orElseThrow(() ->
                            new RuntimeException("Rol no encontrado: " + roleDTO.getRoleName())
                        )
                )
                .collect(Collectors.toList());

            // asignamos la lista de roles al usuario
            user.setRoles(roles);

            // (opcional) para mantener la bidireccionalidad
            roles.forEach(r -> r.getUsers().add(user));
        }

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
    
     public void mergeProfileDTOEnEntidad(ProfileDTO dto, User user) {
        user.setUserName(dto.getUserName());
        user.setSurname(dto.getSurname());
        user.setEmail(dto.getEmail().toLowerCase());
        user.setAvatar(dto.getAvatar());
        user.setPhoneNumber(dto.getPhoneNumber());
    }
    
    public Concert convAEntidad(ConcertDTO dto) {
        Concert concert = new Concert();
        concert.setDate(dto.getDate());
        concert.setPlace(dto.getPlace());
        concert.setUrlTicketSale(dto.getUrlTicketSale());
        return concert;
    }
    
    public Song convAEntidad(SongDTO dto) {
        Song song = new Song();
        song.setTitle(dto.getTitle());
        song.setYearPublication(dto.getYearPublication());
        song.setCoverUrl(dto.getCoverUrl());
        song.setUrl(dto.getUrl());
        song.setType(dto.getType());
        song.setDuration(dto.getDuration());
        song.setTrackNumber(dto.getTrackNumber());     
        
        if (dto.getAlbumId() != null) {
            Album album = albumRepository.findById(dto.getAlbumId()).orElseThrow(() -> new RuntimeException("Álbum no encontrado"));
            song.setAlbum(album);
        } 
        return song;
    }
    
    public Album convAEntidad(AlbumDTO dto) {
        Album album = new Album();
        album.setTitle(dto.getTitle());
        album.setYearPublication(dto.getYearPublication());  
        album.setCoverUrl(dto.getCoverUrl());
        album.setUrl(dto.getUrl());
        
        if (dto.getSongs() != null) {
            List<Song> songs = new ArrayList<>();
            for (SongDTO songDTO : dto.getSongs()) {
                Song song = this.convAEntidad(songDTO);
                song.setAlbum(album);
                songs.add(song);
            }
            album.setSongs(songs);
        }
        return album;
    }
    
    public CreditCard convAEntidad(CreditCardDTO dto) {
        CreditCard creditCard = new CreditCard();
        creditCard.setHolder(dto.getHolder());
        creditCard.setCardNumber(dto.getCardNumber());
        creditCard.setExpirationMonth(dto.getExpirationMonth());
        creditCard.setExpirationYear(dto.getExpirationYear());
        creditCard.setSecurityCode(dto.getSecurityCode());
        
        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            creditCard.setUser(user);
        } 
        return creditCard;
    }

}
