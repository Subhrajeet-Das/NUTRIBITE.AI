package com.nutribite.ai.nutrition.service;

import com.nutribite.ai.model.User;
import com.nutribite.ai.model.enums.ActivityLevel;
import com.nutribite.ai.model.enums.Gender;
import com.nutribite.ai.model.enums.Goal;
import com.nutribite.ai.nutrition.dto.NutritionResponse;
import com.nutribite.ai.profile.entity.UserProfile;
import com.nutribite.ai.profile.repository.UserProfileRepository;
import com.nutribite.ai.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;

@Service @RequiredArgsConstructor @Transactional(readOnly=true)
public class NutritionService {
    private final UserRepository userRepository;
    private final UserProfileRepository profileRepository;
    public NutritionResponse getDailyTargets(){
        Authentication a=SecurityContextHolder.getContext().getAuthentication();
        if(a==null||!a.isAuthenticated()) throw new IllegalArgumentException("User is not authenticated");
        User user=userRepository.findByEmail(a.getName()).orElseThrow(()->new IllegalArgumentException("User not found"));
        UserProfile p=profileRepository.findByUser(user).orElseThrow(()->new IllegalArgumentException("User profile is incomplete"));
        validate(p);
        int age=Period.between(p.getDateOfBirth(),LocalDate.now()).getYears();
        double bmr; Gender g=user.getGender();
        if(g==Gender.MALE) bmr=10*p.getWeightKg()+6.25*p.getHeightCm()-5*age+5;
        else if(g==Gender.FEMALE) bmr=10*p.getWeightKg()+6.25*p.getHeightCm()-5*age-161;
        else bmr=10*p.getWeightKg()+6.25*p.getHeightCm()-5*age;
        double tdee=bmr*multiplier(p.getActivityLevel());
        double calories=goalCalories(tdee,p.getGoal());
        calories=Math.max(1200,calories);
        double protein=p.getWeightKg()*1.6;
        double fat=calories*0.25/9.0;
        double carbs=Math.max(0,(calories-protein*4-fat*9)/4.0);
        double fibre=calories/1000.0*14.0;
        return NutritionResponse.builder().calories(round(calories)).protein(round(protein)).carbs(round(carbs)).fat(round(fat)).fibre(round(fibre)).build();
    }
    private double multiplier(ActivityLevel a){return switch(a){case SEDENTARY->1.20;case LIGHTLY_ACTIVE->1.375;case MODERATELY_ACTIVE->1.55;case VERY_ACTIVE->1.725;case EXTRA_ACTIVE->1.90;};}
    private double goalCalories(double tdee,Goal g){return switch(g){case LOSE_WEIGHT->tdee-400;case MAINTAIN_WEIGHT->tdee;case GAIN_WEIGHT->tdee+300;};}
    private void validate(UserProfile p){if(p.getDateOfBirth()==null)throw new IllegalArgumentException("Date of birth is required");if(p.getDateOfBirth().isAfter(LocalDate.now()))throw new IllegalArgumentException("Date of birth cannot be in the future");if(p.getHeightCm()==null||p.getHeightCm()<80||p.getHeightCm()>250)throw new IllegalArgumentException("Height must be between 80 and 250 cm");if(p.getWeightKg()==null||p.getWeightKg()<20||p.getWeightKg()>300)throw new IllegalArgumentException("Weight must be between 20 and 300 kg");if(p.getActivityLevel()==null)throw new IllegalArgumentException("Activity level is required");if(p.getGoal()==null)throw new IllegalArgumentException("Goal is required");}
    private double round(double v){return Math.round(v*10.0)/10.0;}
}
