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

        // DEBUG
        System.out.println("====================================");
        System.out.println("REGISTER REQUEST");
        System.out.println("Register Password : " + request.getPassword());

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        System.out.println("Encoded Password  : " + encodedPassword);

        user.setPassword(encodedPassword);

        User savedUser = userRepository.save(user);

        System.out.println("Saved Hash        : " + savedUser.getPassword());
        System.out.println("====================================");

        return UserMapper.toResponse(savedUser);
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new InvalidCredentialsException("Invalid email or password"));

        // DEBUG
        System.out.println("====================================");
        System.out.println("LOGIN REQUEST");
        System.out.println("Entered Password : " + request.getPassword());
        System.out.println("Stored Hash      : " + user.getPassword());

        boolean matches = passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        );

        System.out.println("Password Match   : " + matches);
        System.out.println("====================================");

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