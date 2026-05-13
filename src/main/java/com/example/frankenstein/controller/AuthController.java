package com.example.frankenstein.controller;

import com.example.frankenstein.config.JwtService;
import com.example.frankenstein.domain.dto.LoginRequestDTO;
import com.example.frankenstein.domain.dto.LoginResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/login")
    public LoginResponseDTO login(
            @RequestBody LoginRequestDTO dto
    ) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.username(),
                        dto.password()
                )
        );

        String token =
                jwtService.generateToken(dto.username());

        return new LoginResponseDTO(token);
    }
}