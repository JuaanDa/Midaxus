package com.example.Midaxus.services;

import com.example.Midaxus.model.dtos.LoginRequestDTO;
import com.example.Midaxus.model.dtos.LoginResponseDTO;

public interface AuthService {

    LoginResponseDTO login(LoginRequestDTO request);

}