package com.nutribite.ai.profile.service;

import com.nutribite.ai.health.dto.HealthMetricsResponse;
import com.nutribite.ai.profile.dto.request.UpdateProfileRequest;
import com.nutribite.ai.profile.dto.response.ProfileResponse;

public interface UserProfileService {

    ProfileResponse getProfile();

    ProfileResponse createOrUpdateProfile(UpdateProfileRequest request);

    HealthMetricsResponse getHealthMetrics();

}