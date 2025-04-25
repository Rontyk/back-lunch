package com.example.LunchRoulette.service;


import com.example.LunchRoulette.dto.request.AuthRequestDto;
import com.example.LunchRoulette.dto.request.RegisterUserRequestDto;
import com.example.LunchRoulette.dto.response.AuthResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface AuthService {

    void registerUser(RegisterUserRequestDto user);

    AuthResponseDto authenticateUser(AuthRequestDto auth);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;


}
