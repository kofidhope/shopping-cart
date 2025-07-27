package com.dhopecode.shoppingCart.service.order;

import com.dhopecode.shoppingCart.enums.OrderStatus;
import com.dhopecode.shoppingCart.exceptions.ResourceNotFoundException;
import com.dhopecode.shoppingCart.model.*;
import com.dhopecode.shoppingCart.repository.OrderRepository;
import com.dhopecode.shoppingCart.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements iOrderService{

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Override
    public Order placeOrder(Long userId) {
        return null;
    }

    private Order createOrder(Cart cart){
        Order order = new Order();
        //set the user
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return order;
    }

    private List<OrderItem> createOrderItems(Order order, Cart cart){
        return cart.getItems().stream().map(CartItem->{
            Product product =CartItem.getProduct();
            product.setInventory(product.getInventory() - CartItem.getQuantity());
            productRepository.save(product);
            return new OrderItem(
                    order,
                    product,
                    CartItem.getQuantity(),
                    CartItem.getUnitPrice()
            );
        }).toList();
    }

    private BigDecimal calculateTotalAmount(List<OrderItem> orderItemList){
        return orderItemList
                .stream()
                .map(item->item.getPrice()
                        .multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO,BigDecimal::add);
    }

    @Override
    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(()->new ResourceNotFoundException("Order not found"));
    }
}
