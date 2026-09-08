package com.nutribite.ai.profile.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nutribite.ai.health.dto.HealthMetricsResponse;
import com.nutribite.ai.health.service.HealthService;
import com.nutribite.ai.model.User;
import com.nutribite.ai.profile.dto.request.UpdateProfileRequest;
import com.nutribite.ai.profile.dto.response.ProfileResponse;
import com.nutribite.ai.profile.entity.UserProfile;
import com.nutribite.ai.profile.mapper.UserProfileMapper;
import com.nutribite.ai.profile.repository.UserProfileRepository;
import com.nutribite.ai.profile.service.UserProfileService;
import com.nutribite.ai.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserProfileServiceImpl implements UserProfileService {

    private final UserRepository userRepository;
    private final UserProfileRepository profileRepository;
    private final UserProfileMapper mapper;
    private final HealthService healthService;

    @Override
    public ProfileResponse getProfile() {

        User user = getCurrentUser();

        UserProfile profile = profileRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        return mapper.toResponse(profile);
    }

    @Override
    public ProfileResponse createOrUpdateProfile(UpdateProfileRequest request) {

        User user = getCurrentUser();

        UserProfile profile = profileRepository.findByUser(user)
                .orElse(new UserProfile());

        profile.setUser(user);

        mapper.updateProfile(request, profile);

        profileRepository.save(profile);

        return mapper.toResponse(profile);
    }

    @Override
    public HealthMetricsResponse getHealthMetrics() {

        User user = getCurrentUser();

        UserProfile profile = profileRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        return healthService.calculate(user, profile);
    }

    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}