package com.example.book_management.Controller;


import com.example.book_management.Entities.Cart;
import com.example.book_management.Service.Impl.CartService;
import com.example.book_management.dto.CartItemDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/{userId}/add")
    public ResponseEntity<Cart> addItemToCart(HttpServletRequest request, @RequestBody CartItemDTO cartItemDTO, Authentication authentication) {
        Cart cart = cartService.addItemToCart(getToken(request), cartItemDTO);
        return ResponseEntity.ok(cart);
    }

    @GetMapping()
    public ResponseEntity<Cart> getCart(HttpServletRequest request) {
        Cart cart = cartService.getCartByToken(getToken(request));
        return ResponseEntity.ok(cart);
    }


    @DeleteMapping("/{cartId}/remove/{itemId}")
    public ResponseEntity<Cart> removeItemFromCart(HttpServletRequest request,@PathVariable Long itemId) {
        Cart cart = cartService.removeItemFromCart(getToken(request), itemId);
        return ResponseEntity.ok(cart);
    }

    private String getToken(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // Remove "Bearer " prefix
        }
        return token;
    }



}
