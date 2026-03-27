package com.example.Midaxus.controller;

import com.example.Midaxus.model.dtos.auth.LoginRequestDTO;
import com.example.Midaxus.model.dtos.auth.LoginResponseDTO;
import com.example.Midaxus.services.AuthService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // -----------------------
    // LOGIN
    // -----------------------
    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO loginRequest) {
        return authService.login(loginRequest);
    }

    // -----------------------
    // SOLICITAR RESET PASSWORD
    // -----------------------
    @PostMapping("/forgot-password")
    public LoginResponseDTO forgotPassword(@RequestBody Map<String, String> body) {
        return authService.forgotPassword(body.get("email"));
    }

    // -----------------------
    // VALIDAR TOKEN
    // -----------------------
    @GetMapping("/validate-reset-token")
    public LoginResponseDTO validateResetToken(@RequestParam String token) {
        return authService.validateResetToken(token);
    }

    // -----------------------
    // CAMBIAR CONTRASEÑA
    // -----------------------
    @PostMapping("/reset-password")
    public LoginResponseDTO resetPassword(@RequestBody Map<String, String> body) {
        return authService.resetPassword(body.get("token"), body.get("password"));
    }
}