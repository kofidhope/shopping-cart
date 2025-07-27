package com.dhopecode.shoppingCart.service.order;

import com.dhopecode.shoppingCart.model.Order;

public interface iOrderService {
    Order placeOrder(Long userId);
    Order getOrder(Long orderId);
}
