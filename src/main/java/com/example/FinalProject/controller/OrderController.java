package com.example.FinalProject.controller;

import com.example.FinalProject.dto.OrderDto;
import com.example.FinalProject.entity.Order;
import com.example.FinalProject.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/place")
    public OrderDto placeOrder(@RequestParam Long userId, @RequestParam String pickupLocation) {
         return orderService.placeOrder(userId, pickupLocation);
    }
    @GetMapping("/user/{userId}")
    public List<OrderDto> getOrdersByUserId(@PathVariable Long userId) {
        return orderService.getOrdersByUserId(userId);
    }

    @GetMapping("/{orderId}")
    public OrderDto getOrderById(@PathVariable Long orderId) {
        return orderService.getOrderById(orderId);
    }
}
