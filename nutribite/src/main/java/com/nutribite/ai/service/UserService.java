package com.nutribite.ai.service;

import com.nutribite.ai.dto.request.LoginRequest;
import com.nutribite.ai.dto.request.RegisterRequest;
import com.nutribite.ai.dto.response.LoginResponse;
import com.nutribite.ai.dto.response.UserResponse;

public interface UserService {

    UserResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);
}