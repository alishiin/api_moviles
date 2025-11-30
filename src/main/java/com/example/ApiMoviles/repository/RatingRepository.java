package com.example.ApiMoviles.repository;

import com.example.ApiMoviles.model.Rating;
import com.example.ApiMoviles.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByProducto(Product producto);
}
