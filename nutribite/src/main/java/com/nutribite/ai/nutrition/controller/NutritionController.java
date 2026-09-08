package com.nutribite.ai.nutrition.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nutribite.ai.nutrition.dto.NutritionResponse;
import com.nutribite.ai.nutrition.service.NutritionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/nutrition")
@RequiredArgsConstructor
public class NutritionController {

    private final NutritionService nutritionService;

    @GetMapping
    public ResponseEntity<NutritionResponse> getDailyTargets() {

        return ResponseEntity.ok(
                nutritionService.getDailyTargets()
        );
    }
}