package com.example.ApiMoviles.controller;

import com.example.ApiMoviles.model.Cart;
import com.example.ApiMoviles.model.User;
import com.example.ApiMoviles.service.CartService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{userId}")
    public List<Cart> getCartByUser(@PathVariable Long userId) {
        return cartService.findByUserId(userId);
    }
}
