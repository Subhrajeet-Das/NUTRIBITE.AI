package com.nutribite.ai.profile.mapper;

import org.springframework.stereotype.Component;

import com.nutribite.ai.profile.dto.request.UpdateProfileRequest;
import com.nutribite.ai.profile.dto.response.ProfileResponse;
import com.nutribite.ai.profile.entity.UserProfile;

@Component
public class UserProfileMapper {

    public void updateProfile(UpdateProfileRequest request, UserProfile profile) {

        profile.setDateOfBirth(request.getDateOfBirth());
        profile.setHeightCm(request.getHeightCm());
        profile.setWeightKg(request.getWeightKg());
        profile.setTargetWeightKg(request.getTargetWeightKg());

        profile.setActivityLevel(request.getActivityLevel());
        profile.setGoal(request.getGoal());
        profile.setDietType(request.getDietType());
        profile.setFitnessLevel(request.getFitnessLevel());

        profile.setDailyWaterGoal(request.getDailyWaterGoal());
        profile.setSleepGoal(request.getSleepGoal());
    }

    public ProfileResponse toResponse(UserProfile profile) {

        return ProfileResponse.builder()
                .userId(profile.getUser().getId())
                .firstName(profile.getUser().getFirstName())
                .lastName(profile.getUser().getLastName())
                .email(profile.getUser().getEmail())
                .gender(profile.getUser().getGender())

                .dateOfBirth(profile.getDateOfBirth())
                .heightCm(profile.getHeightCm())
                .weightKg(profile.getWeightKg())
                .targetWeightKg(profile.getTargetWeightKg())

                .activityLevel(profile.getActivityLevel())
                .goal(profile.getGoal())
                .dietType(profile.getDietType())
                .fitnessLevel(profile.getFitnessLevel())

                .dailyWaterGoal(profile.getDailyWaterGoal())
                .sleepGoal(profile.getSleepGoal())

                .build();
    }
}