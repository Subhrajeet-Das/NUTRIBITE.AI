package com.nutribite.ai.profile.controller;

import com.nutribite.ai.health.dto.HealthMetricsResponse;
import com.nutribite.ai.profile.dto.request.UpdateProfileRequest;
import com.nutribite.ai.profile.dto.response.ProfileResponse;
import com.nutribite.ai.profile.service.UserProfileService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final UserProfileService profileService;

    @GetMapping
    public ResponseEntity<ProfileResponse> getProfile() {
        return ResponseEntity.ok(profileService.getProfile());
    }

    @PutMapping
    public ResponseEntity<ProfileResponse> updateProfile(
            @Valid @RequestBody UpdateProfileRequest request) {

        return ResponseEntity.ok(
                profileService.createOrUpdateProfile(request));
    }

    @GetMapping("/health")
    public ResponseEntity<HealthMetricsResponse> getHealthMetrics() {
        return ResponseEntity.ok(profileService.getHealthMetrics());
    }
}