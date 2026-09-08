package com.nutribite.ai.profile.dto.request;

import java.time.LocalDate;

import com.nutribite.ai.model.enums.ActivityLevel;
import com.nutribite.ai.model.enums.DietType;
import com.nutribite.ai.model.enums.FitnessLevel;
import com.nutribite.ai.model.enums.Goal;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateProfileRequest {

    @NotNull
    private LocalDate dateOfBirth;

    @NotNull
    private Double heightCm;

    @NotNull
    private Double weightKg;

    private Double targetWeightKg;

    @NotNull
    private ActivityLevel activityLevel;

    @NotNull
    private Goal goal;

    @NotNull
    private DietType dietType;

    @NotNull
    private FitnessLevel fitnessLevel;

    private Double dailyWaterGoal;

    private Double sleepGoal;
}