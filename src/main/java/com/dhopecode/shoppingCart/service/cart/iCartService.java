package com.dhopecode.shoppingCart.service.cart;

import com.dhopecode.shoppingCart.model.Cart;
import com.dhopecode.shoppingCart.model.User;

import java.math.BigDecimal;

public interface iCartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);

    Cart initialiseNewCart(User user);

    Cart getCartByUserId(Long userId);
}
