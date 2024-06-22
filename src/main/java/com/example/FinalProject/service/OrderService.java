package com.example.FinalProject.service;

import com.example.FinalProject.dto.OrderDto;
import com.example.FinalProject.entity.Order;

import java.util.List;

public interface OrderService {
    OrderDto placeOrder(Long userId, String pickupLocation);
    List<OrderDto> getOrdersByUserId(Long userId);
    OrderDto getOrderById(Long orderId);
    void cancelOrder(Long orderId);
}
