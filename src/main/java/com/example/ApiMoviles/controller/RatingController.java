package com.example.ApiMoviles.controller;

import com.example.ApiMoviles.model.Product;
import com.example.ApiMoviles.model.Rating;
import com.example.ApiMoviles.model.User;
import com.example.ApiMoviles.repository.ProductRepository;
import com.example.ApiMoviles.repository.RatingRepository;
import com.example.ApiMoviles.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public RatingController(RatingRepository ratingRepository,
                            UserRepository userRepository,
                            ProductRepository productRepository) {
        this.ratingRepository = ratingRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    // Crear un rating
    @PostMapping("/product/{productId}/user/{userId}")
    public Rating addRating(@PathVariable Long productId,
                            @PathVariable Long userId,
                            @RequestBody Rating rating) {
        Product producto = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        User usuario = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        rating.setProducto(producto);
        rating.setUsuario(usuario);

        return ratingRepository.save(rating);
    }

    // Listar ratings de un producto
    @GetMapping("/product/{productId}")
    public List<Rating> getRatingsByProduct(@PathVariable Long productId) {
        Product producto = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return ratingRepository.findByProducto(producto);
    }
}
