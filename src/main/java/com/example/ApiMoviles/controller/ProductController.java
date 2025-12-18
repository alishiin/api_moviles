package com.example.ApiMoviles.controller;

import com.example.ApiMoviles.dto.ProductResponseDTO;
import com.example.ApiMoviles.model.Product;
import com.example.ApiMoviles.repository.ProductRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto con ID " + id + " no encontrado"));

        return ResponseEntity.ok(convertToDTO(product));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductResponseDTO> createProduct(
            @RequestParam("nombre") String nombre,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("precio") int precio,
            @RequestParam("stock") int stock,
            @RequestParam("tallasDisponibles") String tallasDisponibles,
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam("imagen") MultipartFile imagenFile
    ) throws IOException {
        Product product = new Product();
        product.setNombre(nombre);
        product.setDescripcion(descripcion);
        product.setPrecio(precio);
        product.setStock(stock);
        product.setTallasDisponibles(tallasDisponibles);

        // Por ahora no resolvemos la Category para no tocar más servicios;
        // si necesitas asociar la categoría podemos hacerlo usando ProductService y CategoryRepository.

        if (imagenFile != null && !imagenFile.isEmpty()) {
            product.setImagen(imagenFile.getBytes());
        }

        Product saved = productRepository.save(product);
        return ResponseEntity.ok(convertToDTO(saved));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @PathVariable Long id,
            @RequestParam("nombre") String nombre,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("precio") int precio,
            @RequestParam("stock") int stock,
            @RequestParam("tallasDisponibles") String tallasDisponibles,
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "imagen", required = false) MultipartFile imagenFile
    ) throws IOException {

        // Verificar que el producto existe
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto con ID " + id + " no encontrado"));

        // Actualizar los campos
        existingProduct.setNombre(nombre);
        existingProduct.setDescripcion(descripcion);
        existingProduct.setPrecio(precio);
        existingProduct.setStock(stock);
        existingProduct.setTallasDisponibles(tallasDisponibles);

        // Actualizar imagen solo si se proporciona una nueva
        if (imagenFile != null && !imagenFile.isEmpty()) {
            existingProduct.setImagen(imagenFile.getBytes());
        }

        // Guardar el producto actualizado
        Product savedProduct = productRepository.save(existingProduct);
        return ResponseEntity.ok(convertToDTO(savedProduct));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        // Verificar que el producto existe antes de eliminarlo
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Producto con ID " + id + " no encontrado");
        }

        productRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private ProductResponseDTO convertToDTO(Product product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getNombre(),
                product.getDescripcion(),
                product.getPrecio(),
                product.getStock(),
                product.getTallasDisponibles(),
                product.getCategory() != null ? product.getCategory().getId() : null,
                product.getCategory() != null ? product.getCategory().getNombre() : null,
                product.getImagen() != null && product.getImagen().length > 0
        );
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

    @GetMapping("/test-schema")
    public ResponseEntity<String> testSchema() {
        try {
            List<Product> products = productRepository.findAll();
            StringBuilder info = new StringBuilder();
            info.append("Productos encontrados: ").append(products.size()).append("\n");

            if (!products.isEmpty()) {
                Product firstProduct = products.get(0);
                info.append("Primer producto - ID: ").append(firstProduct.getId()).append("\n");
                info.append("Nombre: ").append(firstProduct.getNombre()).append("\n");
                info.append("Precio: ").append(firstProduct.getPrecio()).append("\n");
                info.append("Stock: ").append(firstProduct.getStock()).append("\n");
                info.append("Tallas: ").append(firstProduct.getTallasDisponibles()).append("\n");
            }

            return ResponseEntity.ok(info.toString());
        } catch (Exception e) {
            return ResponseEntity.ok("Error al acceder a productos: " + e.getMessage());
        }
    }
}
