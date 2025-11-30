package com.example.ApiMoviles.repository;

import com.example.ApiMoviles.model.Cart;
import com.example.ApiMoviles.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUsuario(User usuario);
    List<Cart> findByUsuario_Id(Long usuarioId);
    void deleteByUsuario(User usuario);
}
