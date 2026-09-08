package com.nutribite.ai.health.calculator;

import org.springframework.stereotype.Component;

import com.nutribite.ai.model.enums.Gender;

@Component
public class BMRCalculator {

    /**
     * Calculate BMR using the Mifflin-St Jeor Equation
     */
    public double calculate(
            Gender gender,
            double weightKg,
            double heightCm,
            int age) {

        if (gender == Gender.MALE) {
            return (10 * weightKg)
                    + (6.25 * heightCm)
                    - (5 * age)
                    + 5;
        }

        return (10 * weightKg)
                + (6.25 * heightCm)
                - (5 * age)
                - 161;
    }
}