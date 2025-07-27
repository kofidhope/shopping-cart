package com.dhopecode.shoppingCart.service.order;

import com.dhopecode.shoppingCart.dto.OrderDto;
import com.dhopecode.shoppingCart.model.Order;

import java.util.List;

public interface iOrderService {
    Order placeOrder(Long userId);
    OrderDto getOrder(Long orderId);

    List<OrderDto> getUserOrder(Long UserId);
}
