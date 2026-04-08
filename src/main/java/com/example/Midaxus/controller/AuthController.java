package com.example.Midaxus.controller;

import com.example.Midaxus.model.dtos.auth.LoginRequestDTO;
import com.example.Midaxus.model.dtos.auth.LoginResponseDTO;
import com.example.Midaxus.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    // ✅ Constructor único
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // ✅ LOGIN (único endpoint)
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }

}