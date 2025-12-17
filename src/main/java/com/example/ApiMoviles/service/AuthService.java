package com.example.ApiMoviles.service;

import com.example.ApiMoviles.model.User;
import com.example.ApiMoviles.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * REGISTER - Registrar nuevo usuario y devolver el usuario completo
     */
    public User registerAndGetUser(String name, String email, String password,
                                   String rut, String direccion, String comuna) {
        // Verificar si el email ya existe
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("El email ya está registrado");
        }

        // Crear nuevo usuario
        User newUser = new User();
        newUser.setNombre(name);
        newUser.setEmail(email);
        newUser.setPassword(password);  // En producción usa BCrypt
        newUser.setRut(rut);
        newUser.setDireccion(direccion);

        // Manejar comuna con formato "Santiago - Providencia"
        if (comuna != null && comuna.contains(" - ")) {
            String[] parts = comuna.split(" - ");
            newUser.setRegion(parts[0]);  // "Santiago"
            newUser.setComuna(parts[1]);  // "Providencia"
        } else {
            newUser.setComuna(comuna);
            newUser.setRegion(comuna);
        }

        // ✅ Por defecto todos los nuevos usuarios son USER
        newUser.setRol("USER");

        // Guardar en BD y devolver
        return userRepository.save(newUser);
    }

    /**
     * LOGIN - Autenticar usuario y devolver el usuario completo
     */
    public User loginAndGetUser(String email, String password) {
        // Buscar usuario por email
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado");
        }

        User user = userOpt.get();

        // Verificar contraseña (en producción usa BCrypt)
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        // ✅ Devolver el usuario completo con todos sus datos incluido el ROL
        return user;
    }

    /**
     * OPCIONAL: Si necesitas seguir usando JWT tokens
     * Puedes agregar estos métodos
     */

    // public String register(String name, String email, String password, String comuna, String region) {
    //     User user = registerAndGetUser(name, email, password, "", "", comuna);
    //     return jwtUtil.generateToken(user.getEmail());
    // }

    // public String login(String email, String password) {
    //     User user = loginAndGetUser(email, password);
    //     return jwtUtil.generateToken(user.getEmail());
    // }
}

