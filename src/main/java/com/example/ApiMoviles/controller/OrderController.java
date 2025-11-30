package com.example.ApiMoviles.controller;

import com.example.ApiMoviles.model.Order;
import com.example.ApiMoviles.model.User;
import com.example.ApiMoviles.service.OrderService;
import com.example.ApiMoviles.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserRepository userRepository;

    public OrderController(OrderService orderService, UserRepository userRepository) {
        this.orderService = orderService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public Order createOrder(@RequestParam Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Aquí llamas a OrderService para crear el pedido
        return orderService.createOrder(user, null); // null si aún no pasas items
    }

    @GetMapping("/user/{userId}")
    public List<Order> getOrdersByUser(@PathVariable Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return orderService.findByUser(user);
    }
}
