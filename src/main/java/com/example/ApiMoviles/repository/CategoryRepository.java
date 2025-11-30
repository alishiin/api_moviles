package com.example.ApiMoviles.repository;

import com.example.ApiMoviles.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Puedes agregar métodos personalizados si lo necesitas
}
