package com.nutribite.ai.nutrition.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NutritionResponse {

    private double calories;

    private double protein;

    private double carbs;

    private double fat;

    private double fibre;
}