package com.dhopecode.shoppingCart.repository;

import com.dhopecode.shoppingCart.model.CartItem;
import com.dhopecode.shoppingCart.service.cart.CartItemService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    void deleteAllByCartId(Long id);
}
