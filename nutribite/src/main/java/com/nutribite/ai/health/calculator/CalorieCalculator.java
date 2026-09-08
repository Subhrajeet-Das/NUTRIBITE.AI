package com.nutribite.ai.health.calculator;

import org.springframework.stereotype.Component;

import com.nutribite.ai.model.enums.Goal;

@Component
public class CalorieCalculator {

    /**
     * Calculate recommended daily calories
     */
    public double calculate(double tdee, Goal goal) {

        return switch (goal) {

            case LOSE_WEIGHT -> tdee - 500;

            case MAINTAIN_WEIGHT -> tdee;

            case GAIN_WEIGHT -> tdee + 500;
        };
    }
}