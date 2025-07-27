package com.dhopecode.shoppingCart.dto;

import com.dhopecode.shoppingCart.model.CartItem;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;
@Data
public class CartDto {
    private int cartId;
    private Set<CartItemDto> items;
    private BigDecimal totalAmount;
}
