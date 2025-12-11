package org.example.rideshare.controller;

import java.util.Map;

import org.example.rideshare.model.AuthRequest;
import org.example.rideshare.model.User;
import org.example.rideshare.service.UserService;
import org.example.rideshare.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication APIs", description = "User registration and login endpoints")
public class AuthController {

    private final AuthenticationManager manager;
    private final UserService service;
    private final JwtUtil jwt;

    public AuthController(AuthenticationManager manager, UserService service, JwtUtil jwt) {
        this.manager = manager;
        this.service = service;
        this.jwt = jwt;
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new user",
               description = "Register a new user or driver. Returns the created user with ID.")
    public User register(@RequestBody User user) {
        return service.register(user.getUsername(), user.getPassword(), user.getRole());
    }

    @PostMapping("/login")
    @Operation(summary = "Login user",
               description = "Authenticate user and receive JWT token. Returns token, role, and username.")
    public Map<String, String> login(@RequestBody AuthRequest req) {
        manager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
        UserDetails userDetails = service.loadUserByUsername(req.getUsername());
        String role = userDetails.getAuthorities().iterator().next().getAuthority();
        String token = jwt.generateToken(req.getUsername(), role);
        return Map.of("token", token);
    }
}
