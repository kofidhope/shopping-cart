package com.dhopecode.shoppingCart.repository;

import com.dhopecode.shoppingCart.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
