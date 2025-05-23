/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package edu.dwes.pi_manuelRetamosa_backend.models.daos;

import edu.dwes.pi_manuelRetamosa_backend.models.entities.CartProduct;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

/**
 *
 * @author manue
 */
@Repository
public interface ICartProductRepository extends JpaRepository<CartProduct, Long>{
    
    @Modifying
    @Transactional
    void deleteByCartShoppingId(Long cartShoppingId);
    
    Optional<CartProduct> findByCartShoppingIdAndProductVariantId(Long cartId, Long variantId);
}
