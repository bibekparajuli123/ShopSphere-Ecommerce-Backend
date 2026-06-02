package com.shopsphere.repository;

import com.shopsphere.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface CartItemRepository
        extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByCartCartIdAndProductProductId(
            Long cartId,
            Long productId
    );

    List<CartItem> findByCartCartId(Long cartId);
}