package com.example.ApiMoviles.service;

import com.example.ApiMoviles.model.User;
import com.example.ApiMoviles.repository.UserRepository;
import com.example.ApiMoviles.security.JwtTokenUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtTokenUtil jwtTokenUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
    }


    public String register(String name, String email, String password, String comuna, String region) {

        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("A user with this email already exists");
        }

        User user = new User();
        user.setNombre(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setComuna(comuna);
        user.setRegion(region);

        userRepository.save(user);

        // Return JWT token
        return jwtTokenUtil.generateToken(email);
    }


    public String login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return jwtTokenUtil.generateToken(email);
    }
}
