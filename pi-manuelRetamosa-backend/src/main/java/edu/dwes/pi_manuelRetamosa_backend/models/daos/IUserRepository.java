/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package edu.dwes.pi_manuelRetamosa_backend.models.daos;

import edu.dwes.pi_manuelRetamosa_backend.models.entities.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author manue
 */
@Repository
public interface IUserRepository extends JpaRepository<User, Long>{
    
    Optional<User> findByEmail(String email);
    
    boolean existsByEmail(String email);
}
