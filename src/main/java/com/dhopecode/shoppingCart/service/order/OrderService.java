package com.dhopecode.shoppingCart.service.order;

import com.dhopecode.shoppingCart.dto.OrderDto;
import com.dhopecode.shoppingCart.enums.OrderStatus;
import com.dhopecode.shoppingCart.exceptions.ResourceNotFoundException;
import com.dhopecode.shoppingCart.model.*;
import com.dhopecode.shoppingCart.repository.OrderRepository;
import com.dhopecode.shoppingCart.repository.ProductRepository;
import com.dhopecode.shoppingCart.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements iOrderService{

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;
    private final ModelMapper modelMapper;

    @Override
    public Order placeOrder(Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        Order order = createOrder(cart);
        List<OrderItem> orderItemList = createOrderItems(order, cart);

        order.setOrderItems(new HashSet<>(orderItemList));
        order.setTotalAmount(calculateTotalAmount(orderItemList));
        Order saveOrder = orderRepository.save(order);
        cartService.clearCart(cart.getId());
        return saveOrder;
    }

    private Order createOrder(Cart cart){
        Order order = new Order();
        //set the user
        order.setUser(cart.getUser());
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
    public OrderDto getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .map(this::convertToDto)
                .orElseThrow(()->new ResourceNotFoundException("Order not found"));
    }

    @Override
    public List<OrderDto> getUserOrder(Long userId){
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream().map(this::convertToDto).toList();
    }
    private OrderDto convertToDto(Order order){
        return modelMapper.map(order, OrderDto.class);
    }
}
