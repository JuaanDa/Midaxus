package com.example.Midaxus.controller;

import com.example.Midaxus.model.dtos.LoginRequestDTO;
import com.example.Midaxus.model.dtos.LoginResponseDTO;
import com.example.Midaxus.services.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*") // Para permitir peticiones del Front
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO loginRequest) {
        return authService.login(loginRequest);
    }
}
