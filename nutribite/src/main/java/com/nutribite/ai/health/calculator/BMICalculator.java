package com.nutribite.ai.health.calculator;

import org.springframework.stereotype.Component;

@Component
public class BMICalculator {

    /**
     * BMI = weight(kg) / height(m)^2
     */
    public double calculateBMI(double weightKg, double heightCm) {

        double heightMeter = heightCm / 100.0;

        return weightKg / (heightMeter * heightMeter);
    }

    /**
     * Returns BMI Category
     */
    public String getCategory(double bmi) {

        if (bmi < 18.5)
            return "Underweight";

        if (bmi < 25)
            return "Normal";

        if (bmi < 30)
            return "Overweight";

        return "Obese";
    }

    /**
     * Devine Formula
     * Male reference weight
     */
    public double calculateIdealWeight(double heightCm) {

        double inches = heightCm / 2.54;

        return 50 + (2.3 * (inches - 60));
    }
}