package com.example.ApiMoviles.controller;

import com.example.ApiMoviles.model.Product;
import com.example.ApiMoviles.repository.ProductRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Product> createProduct(
            @RequestParam("nombre") String nombre,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("precio") double precio,
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam("imagen") MultipartFile imagenFile
    ) throws IOException {
        Product product = new Product();
        product.setNombre(nombre);
        product.setDescripcion(descripcion);
        product.setPrecio(precio);

        // Por ahora no resolvemos la Category para no tocar más servicios;
        // si necesitas asociar la categoría podemos hacerlo usando ProductService y CategoryRepository.

        if (imagenFile != null && !imagenFile.isEmpty()) {
            product.setImagen(imagenFile.getBytes());
        }

        Product saved = productRepository.save(product);
        return ResponseEntity.ok(saved);
    }

    @GetMapping(value = "/{id}/imagen")
    public ResponseEntity<byte[]> getProductImage(@PathVariable Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (product.getImagen() == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE)
                .body(product.getImagen());
    }
}
