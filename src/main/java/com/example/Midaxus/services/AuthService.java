package com.example.Midaxus.services;

import com.example.Midaxus.model.dtos.auth.LoginRequestDTO;
import com.example.Midaxus.model.dtos.auth.LoginResponseDTO;
import com.example.Midaxus.model.entities.Admin;
import com.example.Midaxus.model.entities.Student;
import com.example.Midaxus.model.entities.Teacher;
import com.example.Midaxus.model.entities.User;
import com.example.Midaxus.model.mapper.AdminMapper;
import com.example.Midaxus.model.mapper.StudentMapper;
import com.example.Midaxus.model.mapper.TeacherMapper;
import com.example.Midaxus.repositories.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final EmailService emailService;

    public AuthService(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    // -----------------------
    // LOGIN
    // -----------------------
    public LoginResponseDTO login(LoginRequestDTO login) {

        User user = userRepository.findByEmail(login.getEmail()).orElse(null);

        if (user == null) {
            return new LoginResponseDTO(false, "El correo no está registrado", null, null);
        }

        if (!user.getPassword().equals(login.getPassword())) {
            return new LoginResponseDTO(false, "Contraseña incorrecta", null, null);
        }

        Object dto = null;
        switch (user.getUserType()){

            case ADMIN -> dto = AdminMapper.toDTO((Admin) user);
            case STUDENT -> dto = StudentMapper.toDTO((Student) user);
            case TEACHER -> dto = TeacherMapper.toDTO((Teacher) user);

        }
        return new LoginResponseDTO(true,
                                    "Login exitoso",
                                    user.getUserType().name(), dto);
    }

    // -----------------------
    // FORGOT PASSWORD (HTML)
    // -----------------------


    // -----------------------
    // VALIDAR TOKEN
    // -----------------------
//    public LoginResponseDTO validateResetToken(String token) {
//
//        User user = userRepository.findByResetToken(token).orElse(null);
//
//        if (user == null || user.getResetTokenExpiration().before(new Date())) {
//            return new LoginResponseDTO(false, "Token inválido o expirado", null);
//        }
//
//        return new LoginResponseDTO(true, "Token válido", null);
//    }

    // -----------------------
    // RESET PASSWORD
    // -----------------------
//    public LoginResponseDTO resetPassword(String token, String newPassword) {
//
//        User user = userRepository.findByResetToken(token).orElse(null);
//
//        if (user == null || user.getResetTokenExpiration().before(new Date())) {
//            return new LoginResponseDTO(false, "Token inválido o expirado", null);
//        }
//
//        user.setPassword(newPassword);
//        user.setResetToken(null);
//        user.setResetTokenExpiration(null);
//
//        userRepository.save(user);
//
//        return new LoginResponseDTO(true, "Contraseña actualizada correctamente", null);
//    }
}
