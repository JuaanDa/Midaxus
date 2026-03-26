package com.example.Midaxus.services;
import com.example.Midaxus.model.dtos.LoginRequestDTO;
import com.example.Midaxus.model.dtos.LoginResponseDTO;
import com.example.Midaxus.model.entities.User;
import com.example.Midaxus.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LoginResponseDTO login(LoginRequestDTO login) {

        User user = userRepository.findByEmail(login.getEmail())
                .orElse(null);

        if (user == null) {
            return new LoginResponseDTO(false, "Usuario no encontrado", null);
        }

        if (!user.getPassword().equals(login.getPassword())) {
            return new LoginResponseDTO(false, "Contraseña incorrecta", null);
        }

        return new LoginResponseDTO(true, "Login exitoso", user.getRol());
    }
}
