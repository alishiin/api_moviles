package com.example.ApiMoviles.service;

import com.example.ApiMoviles.model.Category;
import com.example.ApiMoviles.model.Product;
import com.example.ApiMoviles.repository.ProductRepository;
import com.example.ApiMoviles.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productoRepository;
    private final CategoryRepository categoriaRepository;

    public ProductService(
            ProductRepository productoRepository,
            CategoryRepository categoriaRepository
    ) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public List<Product> listAll() {
        return productoRepository.findAll();
    }

    public Product findById(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    public List<Product> findByCategoria(Long categoriaId) {
        return productoRepository.findByCategoryId(categoriaId);
    }

    public Product save(Product producto) {
        return productoRepository.save(producto);
    }
}
