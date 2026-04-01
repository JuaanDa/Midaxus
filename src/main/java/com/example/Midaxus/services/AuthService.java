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
import org.springframework.stereotype.Service;

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

        Object dto;
        String role;

        if (user instanceof Teacher t) {
            dto = TeacherMapper.toDTO(t);
            role = "TEACHER";

        } else if (user instanceof Student s) {
            dto = StudentMapper.toDTO(s);
            role = "STUDENT";

        } else if (user instanceof Admin a) {
            dto = AdminMapper.toDTO(a);
            role = "ADMIN";

        } else {
            return new LoginResponseDTO(false, "Tipo de usuario desconocido", null, null);
        }

        return new LoginResponseDTO(true, "Login exitoso", role, dto);
    }
    }

