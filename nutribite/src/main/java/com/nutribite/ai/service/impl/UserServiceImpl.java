package com.nutribite.ai.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nutribite.ai.dto.request.LoginRequest;
import com.nutribite.ai.dto.request.RegisterRequest;
import com.nutribite.ai.dto.response.LoginResponse;
import com.nutribite.ai.dto.response.UserResponse;
import com.nutribite.ai.exception.DuplicateResourceException;
import com.nutribite.ai.exception.InvalidCredentialsException;
import com.nutribite.ai.mapper.UserMapper;
import com.nutribite.ai.model.User;
import com.nutribite.ai.repository.UserRepository;
import com.nutribite.ai.security.JwtService;
import com.nutribite.ai.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public UserResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists.");
        }

        User user = UserMapper.toEntity(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(user);

        return UserMapper.toResponse(savedUser);
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new InvalidCredentialsException("Invalid email or password"));

        boolean matches = passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        );

        if (!matches) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        String token = jwtService.generateToken(user.getEmail());

        return new LoginResponse(
                token,
                "Bearer",
                UserMapper.toResponse(user)
        );
    }
}