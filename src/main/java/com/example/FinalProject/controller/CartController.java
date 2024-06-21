package com.example.FinalProject.controller;

import com.example.FinalProject.dto.CartDto;
import com.example.FinalProject.dto.CartItemDto;
import com.example.FinalProject.entity.Cart;
import com.example.FinalProject.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;

    @GetMapping("/{userId}")
    public ResponseEntity<CartDto> getCart(@PathVariable Long userId) {
        CartDto cartDto = cartService.getCart(userId);
        return ResponseEntity.ok(cartDto);
    }

    @PostMapping("/addProduct/{userId}")
    public ResponseEntity<CartItemDto> addProductToCart(@PathVariable Long userId,
                                                        @RequestParam Long productId,
                                                        @RequestParam int quantity) {
        CartItemDto cartItemDto = cartService.addProductToCart(userId, productId, quantity);
        return ResponseEntity.ok(cartItemDto);
    }

    @DeleteMapping("/removeProduct/{userId}/{productId}")
    public ResponseEntity<Void> removeProductFromCart(@PathVariable Long userId,
                                                      @PathVariable Long productId) {
        cartService.removeProductFromCart(userId, productId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/clearCart/{userId}")
    public ResponseEntity<Void> clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }
}
