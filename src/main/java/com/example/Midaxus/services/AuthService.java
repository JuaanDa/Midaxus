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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Midaxus.util.JwtUtil;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, EmailService emailService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // -----------------------
    // LOGIN
    // -----------------------
    public LoginResponseDTO login(LoginRequestDTO login) {

        User user = userRepository.findByEmail(login.getEmail()).orElse(null);

        if (user == null) {
            return new LoginResponseDTO(false, "El correo no está registrado", null, null, null);
        }

        if (!passwordEncoder.matches(login.getPassword(), user.getPassword())) {
            return new LoginResponseDTO(false, "Contraseña incorrecta", null, null, null);
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
            return new LoginResponseDTO(false, "Tipo de usuario desconocido", null, null, null);
        }

        String token = jwtUtil.generateToken(user.getEmail(), role, user.getEmail(), user.getFirstName());

        return new LoginResponseDTO(true, "Login exitoso", role, token, dto);
    }
    }

