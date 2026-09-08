package com.nutribite.ai.profile.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class HealthResponse {

    private Integer age;

    private Double bmi;

    private Double bmr;

    private Double tdee;
}