package com.dhopecode.shoppingCart.service.cart;

import com.dhopecode.shoppingCart.exceptions.ResourceNotFoundException;
import com.dhopecode.shoppingCart.model.Cart;
import com.dhopecode.shoppingCart.model.CartItem;
import com.dhopecode.shoppingCart.model.Product;
import com.dhopecode.shoppingCart.repository.CartItemRepository;
import com.dhopecode.shoppingCart.repository.CartRepository;
import com.dhopecode.shoppingCart.service.product.IProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CartItemService implements iCartItemService {

    private final CartItemRepository cartItemRepository;
    private final IProductService productService;
    private final iCartService cartService;
    private final CartRepository cartRepository;

    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        // 1. get the carts
        Cart cart = cartService.getCart(cartId);
        // 2. get the products
        Product product = productService.getProductById(productId);
        //3. check if the product already exist in the cart
        CartItem cartItem = cart.getItems()
                .stream()
                .filter(item->item.getProduct().getId().equals(productId))
                .findFirst().orElse(new CartItem());
        //4. if yes then increase the quantity with the requested quantity
        if(cartItem.getId() == null){
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
        // 5. in no then initiate the new cartItem entry
        }else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }
        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        CartItem itemToRemove = cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElseThrow(()-> new ResourceNotFoundException("Product not Found"));
        cart.removeItem(itemToRemove);
        cartRepository.save(cart)
    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {

    }
}
