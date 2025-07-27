package com.dhopecode.shoppingCart.service.order;

import com.dhopecode.shoppingCart.model.Order;

import java.util.List;

public interface iOrderService {
    Order placeOrder(Long userId);
    Order getOrder(Long orderId);

    List<Order> getUserOrder(Long UserId);
}
