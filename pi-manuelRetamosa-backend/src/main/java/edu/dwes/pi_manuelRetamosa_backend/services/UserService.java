/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.dwes.pi_manuelRetamosa_backend.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import edu.dwes.pi_manuelRetamosa_backend.models.DTOs.ProfileDTO;
import edu.dwes.pi_manuelRetamosa_backend.models.DTOs.UserDTO;
import edu.dwes.pi_manuelRetamosa_backend.models.daos.ICartShoppingRepository;
import edu.dwes.pi_manuelRetamosa_backend.models.daos.IRoleRepository;
import edu.dwes.pi_manuelRetamosa_backend.models.daos.IUserRepository;
import edu.dwes.pi_manuelRetamosa_backend.models.entities.CartShopping;
import edu.dwes.pi_manuelRetamosa_backend.models.entities.Role;
import edu.dwes.pi_manuelRetamosa_backend.models.entities.User;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author manue
 */
@Service
public class UserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleRepository roleRepository;
    
    @Autowired
    private ICartShoppingRepository cartShoppingRepository;

    @Autowired
    private ConverterDTO converterDTO;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private Cloudinary cloudinary;

    public List<UserDTO> findAll() {
        List<User> list = userRepository.findAll();
        List<UserDTO> listDTOs = new ArrayList<>();
        for (User u : list) {
            listDTOs.add(converterDTO.convADTO(u));
        }
        return listDTOs;
    }

    public UserDTO findById(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return converterDTO.convADTO(user);
    }

    @Transactional 
    public UserDTO save(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new RuntimeException("Correo electrónico ya está en uso");
        }

        User user = converterDTO.convAEntidad(userDTO);

        user.setUserPassword(passwordEncoder.encode(userDTO.getUserPassword()));
        user.setEnabled(true);
        user.setEmail(userDTO.getEmail().toLowerCase());

        Role defaultRole = roleRepository.findByRoleName("ROLE_USER_REGISTERED")
            .orElseThrow(() -> new RuntimeException("Rol por defecto no encontrado"));
        user.getRoles().add(defaultRole);

        User saved = userRepository.save(user);
        
        CartShopping cart = new CartShopping();
        cart.setUser(saved);
        cart.setCartShoppingState("OPEN");  
        cart.setTotal(0f);                   
        cartShoppingRepository.save(cart);
        return converterDTO.convADTO(saved);
    }
    
    public UserDTO login(String email, String userPassword) {
        User user = userRepository.findByEmail(email.toLowerCase())
            .orElseThrow(() -> new RuntimeException("Correo no registrado"));

        if (!passwordEncoder.matches(userPassword, user.getUserPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        if (!user.isEnabled()) {
            throw new RuntimeException("El usuario no está habilitado");
        }

        return converterDTO.convADTO(user);
    }

    @Transactional
    public void delete(Long id) {
        userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        userRepository.deleteById(id);
    }

    public UserDTO update(Long id, UserDTO dto) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        user.setUserName(dto.getUserName());
        user.setSurname(dto.getSurname());
        user.setEmail(dto.getEmail());
        user.setAvatar(dto.getAvatar());
        user.setPhoneNumber(dto.getPhoneNumber());

        User updated = userRepository.save(user);
        return converterDTO.convADTO(updated);
    }
    
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    

    @Transactional
    public UserDTO updateAvatar(Long id, MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("No se recibió ningún archivo para el avatar");
        }
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        @SuppressWarnings("unchecked")
        Map<String,Object> res = cloudinary.uploader()
          .upload(file.getBytes(), ObjectUtils.asMap(
            "folder",        "avatars",
            "resource_type", "image"
          ));
        user.setAvatar(res.get("secure_url").toString());
        User updated = userRepository.save(user);
        return converterDTO.convADTO(updated);
    }
    
    @Transactional
    public UserDTO updateFromProfileDTO(Long id, ProfileDTO profileDto) {
        User userExistente = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        converterDTO.mergeProfileDTOEnEntidad(profileDto, userExistente);
        User saved = userRepository.save(userExistente);
        return converterDTO.convADTO(saved);
    }
}