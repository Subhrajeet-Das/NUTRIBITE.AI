package com.nutribite.ai.health.calculator;
import com.nutribite.ai.model.enums.Gender; import org.springframework.stereotype.Component;
@Component public class BMRCalculator { public double calculate(Gender gender,double weightKg,double heightCm,int age){double base=(10*weightKg)+(6.25*heightCm)-(5*age); if(gender==Gender.MALE)return base+5; if(gender==Gender.FEMALE)return base-161; return base;} }
