package com.nutribite.ai.nutrition.service;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nutribite.ai.model.User;
import com.nutribite.ai.model.enums.Goal;
import com.nutribite.ai.profile.entity.UserProfile;
import com.nutribite.ai.profile.repository.UserProfileRepository;
import com.nutribite.ai.repository.UserRepository;
import com.nutribite.ai.nutrition.dto.NutritionResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NutritionService {

    private final UserRepository userRepository;
    private final UserProfileRepository profileRepository;

    public NutritionResponse getDailyTargets() {

        // =====================================================
        // GET LOGGED-IN USER
        // =====================================================

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated()) {

            throw new RuntimeException(
                    "User is not authenticated"
            );
        }

        String email = authentication.getName();

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                )
                        );

        // =====================================================
        // GET EXISTING USER PROFILE
        // =====================================================

        UserProfile profile =
                profileRepository.findByUser(user)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User profile not found"
                                )
                        );

        validateProfile(profile);

        // =====================================================
        // AGE
        // =====================================================

        int age =
                Period.between(
                        profile.getDateOfBirth(),
                        LocalDate.now()
                ).getYears();

        // =====================================================
        // BMR
        // Mifflin-St Jeor
        // =====================================================

        double bmr;

        String gender =
                user.getGender() == null
                        ? ""
                        : user.getGender().name();

        if ("MALE".equalsIgnoreCase(gender)) {

            bmr =
                    (10.0 * profile.getWeightKg())
                    + (6.25 * profile.getHeightCm())
                    - (5.0 * age)
                    + 5.0;

        } else if ("FEMALE".equalsIgnoreCase(gender)) {

            bmr =
                    (10.0 * profile.getWeightKg())
                    + (6.25 * profile.getHeightCm())
                    - (5.0 * age)
                    - 161.0;

        } else {

            bmr =
                    (10.0 * profile.getWeightKg())
                    + (6.25 * profile.getHeightCm())
                    - (5.0 * age);
        }

        // =====================================================
        // ACTIVITY
        // =====================================================

        double activityMultiplier =
                getActivityMultiplier(
                        profile.getActivityLevel().name()
                );

        double tdee =
                bmr * activityMultiplier;

        // =====================================================
        // CALORIE TARGET
        // =====================================================

        double calories =
                calculateCalories(
                        tdee,
                        profile.getGoal()
                );

        // Keep a reasonable minimum.
        calories = Math.max(
                calories,
                1200.0
        );

        // =====================================================
        // PROTEIN
        // =====================================================

        double protein =
                profile.getWeightKg() * 1.6;

        // =====================================================
        // FAT
        // 25% of calories
        // =====================================================

        double fat =
                (calories * 0.25) / 9.0;

        // =====================================================
        // CARBS
        // Remaining calories
        // =====================================================

        double proteinCalories =
                protein * 4.0;

        double fatCalories =
                fat * 9.0;

        double remainingCalories =
                calories
                        - proteinCalories
                        - fatCalories;

        double carbs =
                Math.max(
                        0.0,
                        remainingCalories / 4.0
                );

        // =====================================================
        // FIBRE
        // 14g per 1000 kcal
        // =====================================================

        double fibre =
                (calories / 1000.0) * 14.0;

        // =====================================================
        // RESPONSE
        // =====================================================

        return NutritionResponse.builder()

                .calories(round(calories))

                .protein(round(protein))

                .carbs(round(carbs))

                .fat(round(fat))

                .fibre(round(fibre))

                .build();
    }


    // =========================================================
    // ACTIVITY MULTIPLIER
    // =========================================================

    private double getActivityMultiplier(
            String activity
    ) {

        if (activity == null) {
            return 1.20;
        }

        return switch (activity) {

            case "SEDENTARY" ->
                    1.20;

            case "LIGHTLY_ACTIVE" ->
                    1.375;

            case "MODERATELY_ACTIVE" ->
                    1.55;

            case "VERY_ACTIVE" ->
                    1.725;

            case "EXTRA_ACTIVE" ->
                    1.90;

            default ->
                    1.20;
        };
    }


    // =========================================================
    // CALORIE TARGET
    // =========================================================

    private double calculateCalories(
            double tdee,
            Goal goal
    ) {

        if (goal == null) {
            return tdee;
        }

        return switch (goal) {

            case LOSE_WEIGHT ->
                    tdee - 400.0;

            case GAIN_WEIGHT ->
                    tdee + 300.0;

            case MAINTAIN_WEIGHT ->
                    tdee;
        };
    }


    // =========================================================
    // VALIDATION
    // =========================================================

    private void validateProfile(
            UserProfile profile
    ) {

        if (profile.getDateOfBirth() == null) {
            throw new IllegalArgumentException(
                    "Date of birth is required"
            );
        }

        if (profile.getHeightCm() == null ||
                profile.getHeightCm() <= 0) {

            throw new IllegalArgumentException(
                    "Valid height is required"
            );
        }

        if (profile.getWeightKg() == null ||
                profile.getWeightKg() <= 0) {

            throw new IllegalArgumentException(
                    "Valid weight is required"
            );
        }

        if (profile.getActivityLevel() == null) {
            throw new IllegalArgumentException(
                    "Activity level is required"
            );
        }

        if (profile.getGoal() == null) {
            throw new IllegalArgumentException(
                    "Goal is required"
            );
        }
    }


    // =========================================================
    // ROUND
    // =========================================================

    private double round(double value) {

        return Math.round(
                value * 10.0
        ) / 10.0;
    }
}