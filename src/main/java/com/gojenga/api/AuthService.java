package com.gojenga.api;
import com.gojenga.api.LoginDto;

public interface AuthService {
    String login(LoginDto loginDto);
}