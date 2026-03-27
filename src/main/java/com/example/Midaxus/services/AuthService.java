package com.example.Midaxus.services;

import com.example.Midaxus.model.dtos.auth.LoginRequestDTO;
import com.example.Midaxus.model.dtos.auth.LoginResponseDTO;
import com.example.Midaxus.model.entities.User;
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
            return new LoginResponseDTO(false, "El correo no está registrado", null);
        }

        if (!user.getPassword().equals(login.getPassword())) {
            return new LoginResponseDTO(false, "Contraseña incorrecta", null);
        }

        return new LoginResponseDTO(true, "Login exitoso", user.getRol());
    }

    // -----------------------
    // FORGOT PASSWORD (HTML)
    // -----------------------
    public LoginResponseDTO forgotPassword(String email) {

        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            return new LoginResponseDTO(false, "El correo no está registrado", null);
        }

        User user = optionalUser.get();

        // Genera token único
        String token = UUID.randomUUID().toString();
        user.setResetToken(token);

        // Expira en 30 min (1800000 ms)
        user.setResetTokenExpiration(new Date(System.currentTimeMillis() + 1800000));

        userRepository.save(user);

        // URL del Front
        String resetUrl = "http://localhost:8080/reset-password.html?token=" + token;

        // TEMPLATE HTML PERSONALIZADO
        String html = """
                <html>
                <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;">
                    <table align="center" width="600" style="background: white; padding: 30px; border-radius: 8px;">
                        <tr>
                            <td align="center">
                                <img src="img/midaxus-font.png" width="180" alt="Midaxus Logo">
                            </td>
                        </tr>
                        
                        <tr>
                            <td>
                                <h2 style="color: #333;">Restablecer contraseña</h2>
                                
                                <p style="font-size: 15px; color: #555;">
                                    Estimado/a <strong>%s</strong>,
                                </p>
                                
                                <p style="font-size: 15px; color: #555;">
                                    Hemos recibido una solicitud para restablecer su contraseña.
                                </p>
                                
                                <p style="font-size: 15px; color: #555;">
                                    Para continuar, haga clic en el siguiente botón:
                                </p>
                                
                                <p style="text-align: center; margin: 30px 0;">
                                    <a href="%s" style="background-color: #3b9a95; color: white; padding: 12px 20px; 
                                    text-decoration: none; border-radius: 6px; font-size: 16px;">
                                        Restablecer Contraseña
                                    </a>
                                </p>
                                
                                <p style="font-size: 13px; color: #777;">
                                    El enlace expirará en <strong>30 minutos</strong>.
                                </p>
                                
                                <p style="font-size: 13px; color: #777;">
                                    Si usted no realizó esta solicitud, puede ignorar este mensaje. 
                                    Su cuenta permanece segura.
                                </p>
                                
                                <p style="font-size: 14px; color: #555; margin-top: 30px;">
                                    Atentamente,<br>
                                    <strong>Equipo de Soporte Midaxus</strong>
                                </p>
                            </td>
                        </tr>
                    </table>
                </body>
                </html>
                """.formatted(user.getFirstName(), resetUrl);

        try {
            emailService.sendHtmlEmail(
                    user.getEmail(),
                    "Restablecimiento de contraseña - Midaxus",
                    html
            );
        } catch (MessagingException e) {
            return new LoginResponseDTO(false, "Error al enviar el correo. Intente nuevamente.", null);
        }

        return new LoginResponseDTO(true, "Se envió un enlace de recuperación al correo.", null);
    }

    // -----------------------
    // VALIDAR TOKEN
    // -----------------------
    public LoginResponseDTO validateResetToken(String token) {

        User user = userRepository.findByResetToken(token).orElse(null);

        if (user == null || user.getResetTokenExpiration().before(new Date())) {
            return new LoginResponseDTO(false, "Token inválido o expirado", null);
        }

        return new LoginResponseDTO(true, "Token válido", null);
    }

    // -----------------------
    // RESET PASSWORD
    // -----------------------
    public LoginResponseDTO resetPassword(String token, String newPassword) {

        User user = userRepository.findByResetToken(token).orElse(null);

        if (user == null || user.getResetTokenExpiration().before(new Date())) {
            return new LoginResponseDTO(false, "Token inválido o expirado", null);
        }

        user.setPassword(newPassword);
        user.setResetToken(null);
        user.setResetTokenExpiration(null);

        userRepository.save(user);

        return new LoginResponseDTO(true, "Contraseña actualizada correctamente", null);
    }
}
