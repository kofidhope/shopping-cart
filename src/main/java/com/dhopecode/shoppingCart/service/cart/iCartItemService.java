package com.dhopecode.shoppingCart.service.cart;

public interface iCartItemService {
    void addItemToCart(Long cartId ,Long productId,int quantity);
    void removeItemFromCart(Long cartId,Long productId);
    void updateItemQuantity(Long cartId ,Long productId,int quantity);
}
