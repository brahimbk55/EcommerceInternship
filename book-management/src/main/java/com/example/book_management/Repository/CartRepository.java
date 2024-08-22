package com.example.book_management.Repository;

import com.example.book_management.Entities.App_user;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.book_management.Entities.Cart;

import java.util.Optional;


public interface CartRepository extends JpaRepository<Cart, Integer> {
    Optional<Cart> findByUserId(Long userId);
}
