package com.example.ApiMoviles.service;

import com.example.ApiMoviles.model.Cart;
import com.example.ApiMoviles.model.Product;
import com.example.ApiMoviles.model.User;
import com.example.ApiMoviles.repository.CartRepository;
import com.example.ApiMoviles.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartRepository carritoRepo;
    private final ProductRepository productoRepo;

    public CartService(CartRepository carritoRepo, ProductRepository productoRepo) {
        this.carritoRepo = carritoRepo;
        this.productoRepo = productoRepo;
    }

    // Obtener carrito por objeto User
    public List<Cart> getCarrito(User usuario) {
        return carritoRepo.findByUsuario(usuario);
    }

    // Obtener carrito por userId
    public List<Cart> getCarritoByUserId(Long userId) {
        return carritoRepo.findByUsuario_Id(userId);
    }

    // Agregar un producto al carrito
    public Cart addItem(User usuario, Long productoId, int cantidad) {
        Product p = productoRepo.findById(productoId).orElse(null);
        if (p == null) return null;

        Cart item = new Cart();
        item.setUsuario(usuario);
        item.setProducto(p);
        item.setCantidad(cantidad);

        return carritoRepo.save(item);
    }

    // Limpiar carrito
    public void clearCarrito(User usuario) {
        carritoRepo.deleteByUsuario(usuario);
    }
}
