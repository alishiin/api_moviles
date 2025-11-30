package com.example.ApiMoviles.service;

import com.example.ApiMoviles.model.User;
import com.example.ApiMoviles.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository usuarioRepository;

    public UserService(UserRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Optional<User> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public User findById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public User save(User usuario) {
        return usuarioRepository.save(usuario);
    }
}
