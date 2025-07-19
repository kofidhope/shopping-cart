package com.dhopecode.shoppingCart.repository;

import com.dhopecode.shoppingCart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {
}
