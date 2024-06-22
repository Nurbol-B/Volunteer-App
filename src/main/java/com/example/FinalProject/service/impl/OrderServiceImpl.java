package com.example.FinalProject.service.impl;

import com.example.FinalProject.dto.OrderDto;
import com.example.FinalProject.entity.*;
import com.example.FinalProject.mapper.OrderMapper;
import com.example.FinalProject.repository.*;
import com.example.FinalProject.service.BonusHistoryService;
import com.example.FinalProject.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final JavaMailSender mailSender;
    private final OrderMapper orderMapper;
    private final UserRepository userRepository;
    private final BonusHistoryService bonusHistoryService;

    @Transactional
    public OrderDto placeOrder(Long userId, String pickupLocation) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Корзина не найден"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        BigDecimal totalAmount = cart.getItems().stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal currentBalance = user.getBalance();
        if (currentBalance.compareTo(totalAmount) < 0) {
            throw new RuntimeException("Недостаточно средств на балансе");
        }

        Order order = new Order();
        order.setPickupLocation(pickupLocation);
        order.setTotalAmount(totalAmount);
        order.setUniqueCode(generateUniqueCode());
        order.setUser(user);

        orderRepository.save(order);

        cart.getItems().clear();
        cartRepository.save(cart);
        BigDecimal newBalance = currentBalance.subtract(totalAmount);
        user.setBalance(newBalance);
        userRepository.save(user);
        bonusHistoryService.addBonusHistory(
                user.getId(),
                totalAmount.negate(),
                currentBalance,
                newBalance,
                "Заказ выполнен: " + order.getId()
        );

        String subject = "Подтверждение заказа";
        String text = String.format(
                "Ваш заказ успешно оформлен!\n\nМесто получения: %s\nУникальный код: %s\n\nСпасибо за покупку!",
                order.getPickupLocation(),
                order.getUniqueCode()
        );
        sendOrderConfirmationEmail(user.getEmail(), subject, text);
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
    @Override
    public void cancelOrder(Long orderId) {
        OrderDto orderDto = getOrderById(orderId);
        orderDto.setRemoveDate(LocalDateTime.now());
        orderRepository.save(orderMapper.toEntity(orderDto));
    }

}
