package com.example.Midaxus.services;

import com.example.Midaxus.model.dtos.LoginRequestDTO;
import com.example.Midaxus.model.dtos.LoginResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Override
    public LoginResponseDTO login(LoginRequestDTO request) {

        String username = request.getUsername();
        String password = request.getPassword();

        // ADMIN
        if ("decano@gmail.com".equals(username) && "Unbosqu3".equals(password)) {
            return new LoginResponseDTO(username, "ADMIN");
        }

        // TEACHER
        if ("profe1@gmail.com".equals(username) && "12345".equals(password)) {
            return new LoginResponseDTO(username, "TEACHER");
        }

        // STUDENT
        if ("est123@gmail.com".equals(username) && "12345".equals(password)) {
            return new LoginResponseDTO(username, "STUDENT");
        }

        throw new RuntimeException("Credenciales incorrectas");
    }
}