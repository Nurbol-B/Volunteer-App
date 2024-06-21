package com.example.FinalProject.service;

import com.example.FinalProject.dto.CartDto;
import com.example.FinalProject.dto.CartItemDto;
import com.example.FinalProject.entity.Cart;
import com.example.FinalProject.entity.CartItem;

public interface CartService {
    CartDto getCart(Long userId);
    CartItemDto addProductToCart(Long userId, Long productId, int quantity);
    void removeProductFromCart(Long userId, Long productId);
    void clearCart(Long userId);
}
