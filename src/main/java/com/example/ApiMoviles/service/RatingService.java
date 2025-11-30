package com.example.ApiMoviles.service;

import com.example.ApiMoviles.model.Product;
import com.example.ApiMoviles.model.Rating;
import com.example.ApiMoviles.repository.ProductRepository;
import com.example.ApiMoviles.repository.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {

    private final RatingRepository valoracionRepository;
    private final ProductRepository productoRepository;

    public RatingService(
            RatingRepository valoracionRepository,
            ProductRepository productoRepository
    ) {
        this.valoracionRepository = valoracionRepository;
        this.productoRepository = productoRepository;
    }

    public List<Rating> findByProducto(Long productoId) {
        Product p = productoRepository.findById(productoId).orElse(null);
        if (p == null) return List.of();
        return valoracionRepository.findByProducto(p);
    }

    public Rating save(Rating valoracion) {
        return valoracionRepository.save(valoracion);
    }
}
