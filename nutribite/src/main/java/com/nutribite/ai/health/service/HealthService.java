package com.nutribite.ai.health.service;

import com.nutribite.ai.health.dto.HealthMetricsResponse;
import com.nutribite.ai.model.User;
import com.nutribite.ai.profile.entity.UserProfile;

public interface HealthService {

    HealthMetricsResponse calculate(User user, UserProfile profile);

}