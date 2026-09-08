package com.nutribite.ai.health.util;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.stereotype.Component;

@Component
public class HealthUtils {

    /**
     * Calculate age from date of birth
     */
    public int calculateAge(LocalDate dateOfBirth) {

        if (dateOfBirth == null) {
            return 0;
        }

        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    /**
     * Round a decimal value to 2 places
     */
    public double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}