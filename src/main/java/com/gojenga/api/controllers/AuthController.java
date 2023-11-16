package com.gojenga.api.controllers;

import com.gojenga.api.AuthService;
import com.gojenga.api.models.JWTAuthResponse;
import com.gojenga.api.models.RegisterDto;
import lombok.AllArgsConstructor;
import com.gojenga.api.models.LoginDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;

    // Build Login REST API
    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponse> authenticate(@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);

        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return ResponseEntity.ok(jwtAuthResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<Boolean> register(@RequestBody RegisterDto registerDto) {
        Boolean registerResponse = authService.register(registerDto);

        return ResponseEntity.ok(registerResponse);
    }
}
