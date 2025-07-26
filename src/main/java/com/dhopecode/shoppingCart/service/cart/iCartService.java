package com.dhopecode.shoppingCart.service.cart;

import com.dhopecode.shoppingCart.model.Cart;

import java.math.BigDecimal;

public interface iCartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);

    long initialiseNewCart();
}
