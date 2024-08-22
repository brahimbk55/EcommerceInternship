package com.example.book_management.Service;

import com.example.book_management.Entities.Cart;
import com.example.book_management.Entities.CartItem;
import com.example.book_management.dto.CartItemDTO;

public interface ICartService {
    Cart getCartByToken(String token);
    Cart addItemToCart(String token, CartItemDTO dto);
    Cart removeItemFromCart(String token, Long itemId);
    void clearCart(String token);
}
