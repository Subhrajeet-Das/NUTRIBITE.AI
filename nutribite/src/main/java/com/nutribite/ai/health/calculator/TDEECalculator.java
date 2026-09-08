package com.nutribite.ai.health.calculator;

import org.springframework.stereotype.Component;

import com.nutribite.ai.model.enums.ActivityLevel;

@Component
public class TDEECalculator {

    /**
     * Calculate Total Daily Energy Expenditure
     */
    public double calculate(double bmr, ActivityLevel activityLevel) {

        double multiplier = switch (activityLevel) {

            case SEDENTARY -> 1.20;

            case LIGHTLY_ACTIVE -> 1.375;

            case MODERATELY_ACTIVE -> 1.55;

            case VERY_ACTIVE -> 1.725;

            case EXTRA_ACTIVE -> 1.90;
        };

        return bmr * multiplier;
    }
}