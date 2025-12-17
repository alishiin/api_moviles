package com.example.ApiMoviles.controller;

import com.example.ApiMoviles.model.User;
import com.example.ApiMoviles.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * REGISTER - Registro de nuevo usuario
     * POST /auth/register
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
        try {
            // ✅ Obtener el usuario registrado completo
            User user = authService.registerAndGetUser(
                    body.get("name"),
                    body.get("email"),
                    body.get("password"),
                    body.get("rut"),
                    body.get("direccion"),
                    body.get("comuna")
            );

            // ✅ Devolver información completa del usuario
            Map<String, Object> response = new HashMap<>();
            response.put("id", user.getId());
            response.put("nombre", user.getNombre());
            response.put("email", user.getEmail());
            response.put("rut", user.getRut());
            response.put("direccion", user.getDireccion());
            response.put("comuna", user.getComuna());
            response.put("region", user.getRegion());
            response.put("rol", user.getRol() != null ? user.getRol() : "USER");

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * LOGIN - Inicio de sesión
     * POST /auth/login
     *
     * ✅ CAMBIO IMPORTANTE: Ahora devuelve el usuario completo con el campo ROL
     * en lugar de solo un token JWT
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        try {
            // ✅ Obtener el usuario completo en lugar de solo el token
            User user = authService.loginAndGetUser(
                    body.get("email"),
                    body.get("password")
            );

            // ✅ Devolver información completa del usuario incluyendo ROL
            Map<String, Object> response = new HashMap<>();
            response.put("id", user.getId());
            response.put("nombre", user.getNombre());
            response.put("email", user.getEmail());
            response.put("rut", user.getRut());
            response.put("direccion", user.getDireccion());
            response.put("comuna", user.getComuna());
            response.put("region", user.getRegion());

            // ✅✅✅ ESTE ES EL CAMPO MÁS IMPORTANTE ✅✅✅
            // Sin este campo, la app no sabrá si es admin o usuario normal
            response.put("rol", user.getRol() != null ? user.getRol() : "USER");

            // Opcional: Si necesitas devolver un token JWT también
            // response.put("token", jwtUtil.generateToken(user.getEmail()));

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}

