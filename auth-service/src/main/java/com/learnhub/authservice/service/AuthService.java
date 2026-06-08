package com.learnhub.authservice.service;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.learnhub.authservice.config.AppConfig;
import com.learnhub.authservice.dto.LoginRequest;
import com.learnhub.authservice.dto.RegisterRequest;
import com.learnhub.authservice.model.User;
import com.learnhub.authservice.repository.UserRepository;
import com.learnhub.authservice.security.JwtService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;
    public void register(RegisterRequest request) {

       if(userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("Student")
                .build();

        userRepository.save(user);
    }

    public String login(LoginRequest request) {
            User user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                throw new RuntimeException("Invalid credentials");
            }
            return jwtService.generateToken(user);
    }

}
