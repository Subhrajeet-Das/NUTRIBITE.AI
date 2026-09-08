package com.nutribite.ai.health.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HealthMetricsResponse {

    private int age;

    private double bmi;

    private String bmiCategory;

    private double idealWeight;

    private double bmr;

    private double tdee;

    private double recommendedCalories;
}