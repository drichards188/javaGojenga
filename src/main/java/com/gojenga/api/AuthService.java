package com.gojenga.api;
import com.gojenga.api.models.LoginDto;
import com.gojenga.api.models.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);
    Boolean register(RegisterDto registerDto);
}