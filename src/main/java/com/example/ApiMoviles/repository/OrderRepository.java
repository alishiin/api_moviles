package com.example.ApiMoviles.repository;

import com.example.ApiMoviles.model.Order;
import com.example.ApiMoviles.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
}
