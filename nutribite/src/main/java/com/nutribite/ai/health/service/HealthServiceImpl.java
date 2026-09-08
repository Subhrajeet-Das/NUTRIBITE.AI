package com.nutribite.ai.health.service;

import org.springframework.stereotype.Service;

import com.nutribite.ai.health.calculator.BMICalculator;
import com.nutribite.ai.health.calculator.BMRCalculator;
import com.nutribite.ai.health.calculator.CalorieCalculator;
import com.nutribite.ai.health.calculator.TDEECalculator;
import com.nutribite.ai.health.dto.HealthMetricsResponse;
import com.nutribite.ai.health.util.HealthUtils;
import com.nutribite.ai.model.User;
import com.nutribite.ai.profile.entity.UserProfile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HealthServiceImpl implements HealthService {

    private final BMICalculator bmiCalculator;
    private final BMRCalculator bmrCalculator;
    private final TDEECalculator tdeeCalculator;
    private final CalorieCalculator calorieCalculator;
    private final HealthUtils healthUtils;

    @Override
    public HealthMetricsResponse calculate(User user, UserProfile profile) {

        int age = healthUtils.calculateAge(profile.getDateOfBirth());

        double bmi = bmiCalculator.calculateBMI(
                profile.getWeightKg(),
                profile.getHeightCm());

        String bmiCategory = bmiCalculator.getCategory(bmi);

        double idealWeight = bmiCalculator.calculateIdealWeight(
                profile.getHeightCm());

        double bmr = bmrCalculator.calculate(
                user.getGender(),
                profile.getWeightKg(),
                profile.getHeightCm(),
                age);

        double tdee = tdeeCalculator.calculate(
                bmr,
                profile.getActivityLevel());

        double recommendedCalories =
                calorieCalculator.calculate(
                        tdee,
                        profile.getGoal());

        return HealthMetricsResponse.builder()
                .age(age)
                .bmi(healthUtils.round(bmi))
                .bmiCategory(bmiCategory)
                .idealWeight(healthUtils.round(idealWeight))
                .bmr(healthUtils.round(bmr))
                .tdee(healthUtils.round(tdee))
                .recommendedCalories(
                        healthUtils.round(recommendedCalories))
                .build();
    }
}