package com.learnhub.authservice.security;

import org.springframework.stereotype.Service;

import com.learnhub.authservice.model.User;

@Service
public class JwtService {

    public static String generateToken(User user) {
        // In a real application, use a library like jjwt to generate a JWT token
        // Here we return a dummy token for demonstration purposes
        return "dummy-jwt-token-for-user-" + user.getEmail();   
    }
    
}
