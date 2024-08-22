package com.example.book_management.Service.Impl;

import com.example.book_management.Entities.App_user;
import com.example.book_management.Entities.Cart;
import com.example.book_management.Entities.CartItem;
import com.example.book_management.Repository.CartItemRepository;
import com.example.book_management.Repository.CartRepository;
import com.example.book_management.Repository.ProductRepository;
import com.example.book_management.Repository.UserRepository;
import com.example.book_management.Service.ICartService;
import com.example.book_management.dto.CartItemDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class CartService implements ICartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private JWTServiceImpl jwtService;

    @Override
    public Cart getCartByToken(String token) {
        Long userId = jwtService.extractUserId(token);

        // Check if cart already exists for the user
        return cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    // Create a new cart
                    Cart newCart = new Cart();
                    App_user user = userRepository.findById(userId)
                            .orElseThrow(() -> new RuntimeException("User not found"));

                    // Set user to the new cart
                    newCart.setUser(user);

                    // Save the new cart
                    Cart savedCart = cartRepository.save(newCart);

                    // Update user with the new cart
                    user.setCart(savedCart);
                    userRepository.save(user);

                    return savedCart;
                });
    }

    @Override
    public Cart addItemToCart(String token, CartItemDTO dto) {
        // Find the user and cart as needed
        // Create CartItem from dto
        CartItem item = new CartItem();
        item.setProduct(productRepository.findById(dto.getProductId()).orElseThrow(() -> new RuntimeException("Product not found")));
        item.setQuantity(dto.getQuantity());
        item.setPrice(item.getProduct().getPrice().multiply(BigDecimal.valueOf(dto.getQuantity())));

        // Add item to cart and save
        Cart cart = getCartByToken(token);
        cart.addItem(item);
        return cartRepository.save(cart);
    }

    @Override
    public Cart removeItemFromCart(String token, Long itemId) {
        Cart cart = getCartByToken(token);
        cart.getItems().removeIf(item -> item.getId().equals(itemId));
        return cartRepository.save(cart);
    }

    @Override
    public void clearCart(String token) {
        Cart cart = getCartByToken(token);
        cart.getItems().clear();
        cartRepository.save(cart);
    }
}
