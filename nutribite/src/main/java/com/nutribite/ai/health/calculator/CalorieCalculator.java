package com.nutribite.ai.health.calculator;
import com.nutribite.ai.model.enums.Goal; import org.springframework.stereotype.Component;
@Component public class CalorieCalculator { public double calculate(double tdee, Goal goal){ if(goal==null)return tdee; return switch(goal){case LOSE_WEIGHT->tdee-400;case MAINTAIN_WEIGHT->tdee;case GAIN_WEIGHT->tdee+300;}; } }
