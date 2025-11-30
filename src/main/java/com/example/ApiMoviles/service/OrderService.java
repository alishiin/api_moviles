package com.example.ApiMoviles.service;

import com.example.ApiMoviles.model.Cart;
import com.example.ApiMoviles.model.Order;
import com.example.ApiMoviles.model.User;
import com.example.ApiMoviles.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // Crear una orden
    public Order createOrder(User user, List<Cart> items) {
        Order order = new Order();
        order.setUser(user);
        order.setItems(items); // Asegúrate que Order tenga setItems(List<Cart>)
        return orderRepository.save(order);
    }

    // Buscar ordenes por objeto User
    public List<Order> findByUser(User user) {
        return orderRepository.findByUser(user);
    }

    // Buscar ordenes por userId
    public List<Order> findByUserId(Long userId) {
        return orderRepository.findByUser_Id(userId);
    }
}
