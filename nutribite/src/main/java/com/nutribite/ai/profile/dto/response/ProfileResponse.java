package com.nutribite.ai.profile.dto.response;

import java.time.LocalDate;

import com.nutribite.ai.model.enums.ActivityLevel;
import com.nutribite.ai.model.enums.DietType;
import com.nutribite.ai.model.enums.FitnessLevel;
import com.nutribite.ai.model.enums.Goal;
import com.nutribite.ai.model.enums.Gender;

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
public class ProfileResponse {

    private Long userId;

    private String firstName;

    private String lastName;

    private String email;

    private Gender gender;

    private LocalDate dateOfBirth;

    private Double heightCm;

    private Double weightKg;

    private Double targetWeightKg;

    private ActivityLevel activityLevel;

    private Goal goal;

    private DietType dietType;

    private FitnessLevel fitnessLevel;

    private Double dailyWaterGoal;

    private Double sleepGoal;
}