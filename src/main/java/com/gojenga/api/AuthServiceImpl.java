package com.gojenga.api;

import com.gojenga.api.models.LoginDto;
import com.gojenga.api.models.RegisterDto;
import com.gojenga.api.models.User;
import com.gojenga.api.repository.UserRepository;
import com.gojenga.api.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;


    public AuthServiceImpl(
            JwtTokenProvider jwtTokenProvider,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String login(LoginDto loginDto) {
    try {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        return token;
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
    }

    @Override
    public Boolean register(RegisterDto registerDto) {
        try {
            User user = new User();
            user.setId(3L);
            user.setUsername(registerDto.getUsername());
            user.setEmail(registerDto.getEmail());
            user.setName(registerDto.getName());
            user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

            userRepository.save(user);


            return true;
        } catch (Exception e) {
            System.out.println("---> " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
