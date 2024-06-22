package com.example.FinalProject.service.impl;

import com.example.FinalProject.dto.CartDto;
import com.example.FinalProject.dto.CartItemDto;
import com.example.FinalProject.entity.*;
import com.example.FinalProject.mapper.CartItemMapper;
import com.example.FinalProject.mapper.CartMapper;
import com.example.FinalProject.repository.*;
import com.example.FinalProject.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;
    public final UserRepository userRepository;

    @Override
    public CartDto getCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId).orElseGet(() -> {
            User user = userRepository.findByIdAndRemoveDateIsNull(userId)
                    .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
            Cart newCart = new Cart();
            newCart.setUser(user);
            return cartRepository.save(newCart);
        });
        return cartMapper.toDto(cart);
    }

    @Override
    public CartItemDto addProductToCart(Long userId, Long productId, int quantity) {
        CartDto cartDto = getCart(userId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Продукт не найден"));

        Optional<CartItemDto> existingItem = cartDto.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItemDto cartItemDto = existingItem.get();
            cartItemDto.setQuantity(cartItemDto.getQuantity() + quantity);
            return cartItemMapper.toDto(cartItemRepository.save(cartItemMapper.toEntity(cartItemDto)));
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cartMapper.toEntity(cartDto));
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            cartDto.getItems().add(cartItemMapper.toDto(cartItemRepository.save(newItem)));
            return cartItemMapper.toDto(newItem);
        }
    }

    public void removeProductFromCart(Long userId, Long productId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Корзина для пользователя с id " + userId + " не найдена!"));
        cart.getItems().removeIf(item -> item.getProduct().getId().equals(productId));
        cartRepository.save(cart);
    }
    @Override
    public void clearCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Корзина для пользователя с id " + userId + " не найдена!"));
        cart.getItems().clear();
        cartRepository.save(cart);
    }
}
