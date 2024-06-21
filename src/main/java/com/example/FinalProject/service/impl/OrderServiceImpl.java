package com.example.FinalProject.service.impl;

import com.example.FinalProject.dto.OrderDto;
import com.example.FinalProject.entity.*;
import com.example.FinalProject.mapper.OrderMapper;
import com.example.FinalProject.repository.*;
import com.example.FinalProject.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final JavaMailSender mailSender;
    private final OrderMapper orderMapper;

    @Transactional
    public OrderDto placeOrder(Long userId, String pickupLocation) {
        Optional<Cart> cartOpt = cartRepository.findByUserId(userId);
        if (!cartOpt.isPresent()) {
            throw new IllegalArgumentException("Корзина для пользователя с id " + userId + " не найдена!");
        }

        Cart cart = cartOpt.get();
        User user = cart.getUser();

        Order order = new Order();
        order.setUser(user);
        order.setPickupLocation(pickupLocation);
        order.setUniqueCode(generateUniqueCode());

        List<OrderItem> orderItems = cart.getItems().stream().map(cartItem -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            return orderItem;
        }).collect(Collectors.toList());

        order.setItems(orderItems);
        order.setTotalAmount(orderItems.stream()
                .map(OrderItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add));

        orderRepository.save(order);

        String subject = "Подтверждение заказа";
        String text = String.format(
                "Ваш заказ успешно оформлен!\n\nМесто получения: %s\nУникальный код: %s\n\nСпасибо за покупку!",
                order.getPickupLocation(),
                order.getUniqueCode()
        );
        sendOrderConfirmationEmail(user.getEmail(), subject, text);

        cart.getItems().clear();
        cartRepository.save(cart);

        return orderMapper.toDto(order);
    }


    private void sendOrderConfirmationEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
    private String generateUniqueCode() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[6];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
    public List<OrderDto> getOrdersByUserId(Long userId) {
        return orderMapper.toDtoList(orderRepository.findByUserId(userId));
    }

    public OrderDto getOrderById(Long orderId) {
        return orderMapper.toDto(orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Заказ с id " + orderId + " не найден!")));
    }
}
