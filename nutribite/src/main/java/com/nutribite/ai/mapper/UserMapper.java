package com.nutribite.ai.mapper;

import com.nutribite.ai.dto.request.RegisterRequest;
import com.nutribite.ai.dto.response.UserResponse;
import com.nutribite.ai.model.User;
import com.nutribite.ai.model.enums.Role;

public class UserMapper {

    private UserMapper() {
    }

    public static User toEntity(RegisterRequest request) {
        User user = new User();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setGender(request.getGender() == null ? com.nutribite.ai.model.enums.Gender.OTHER : request.getGender());

        // Default role for every new user
        user.setRole(Role.USER);

        return user;
    }

    public static UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole(),
                user.getGender()
        );
    }
}