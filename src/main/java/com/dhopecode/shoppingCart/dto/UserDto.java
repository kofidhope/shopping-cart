package com.dhopecode.shoppingCart.dto;

import com.dhopecode.shoppingCart.model.Cart;
import com.dhopecode.shoppingCart.model.Order;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<OrderDto> orders;
    private CartDto cart;
}
